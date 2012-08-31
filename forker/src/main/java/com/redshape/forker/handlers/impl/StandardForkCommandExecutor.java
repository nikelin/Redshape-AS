package com.redshape.forker.handlers.impl;

import com.redshape.forker.IForkCommand;
import com.redshape.forker.IForkCommandResponse;
import com.redshape.forker.ProcessException;
import com.redshape.forker.commands.ErrorResponse;
import com.redshape.forker.events.CommandRequestEvent;
import com.redshape.forker.events.CommandResponseEvent;
import com.redshape.forker.events.ExecutorStartedEvent;
import com.redshape.forker.handlers.IForkCommandExecutor;
import com.redshape.forker.handlers.IForkCommandHandler;
import com.redshape.forker.protocol.processor.IForkProtocolProcessor;
import com.redshape.utils.Commons;
import com.redshape.utils.IFilter;
import com.redshape.utils.events.AbstractEventDispatcher;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/27/12
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class StandardForkCommandExecutor extends AbstractEventDispatcher implements IForkCommandExecutor, Runnable {

    private static final Logger log = Logger.getLogger(StandardForkCommandExecutor.class);

    protected class ResponsesListenerThread implements Callable<Void> {

        @Override
        public Void call() {
            while ( isStarted() ) {
                IForkCommandResponse response = processor.getResultsQueue().peekResponse();
                /**
                 * @TODO: add some kind of I/O wait to optimize CPU loadage
                 */
                if ( response == null ) {
                    continue;
                }

                raiseEvent( new CommandResponseEvent(response) );
            }

            return null;
        }
    }

    protected class CommandsListenerThread implements Callable<Void> {

        @Override
        public Void call() {
            try {
                while ( isStarted() ) {
                    IForkCommand command = processor.getResultsQueue().peekRequest();
                    /**
                     * @TODO: add some kind of I/O wait to optimize CPU loadage
                     */
                    if ( command == null ) {
                        continue;
                    }

                    raiseEvent( new CommandRequestEvent(command) );

                    IForkCommandResponse response = executeCommand(command);
                    if ( response == null ) {
                        response = new ErrorResponse("Unsupported command requested", IForkCommandResponse.Status.FAIL);
                    }

                    processor.getWorkQueue().collectResponse(response);
                }

                return null;
            } catch ( ProcessException e ) {
                throw new IllegalStateException( e.getMessage(), e );
            }
        }
    }

    public class CommandExecutionCallable<T extends IForkCommandResponse> implements Callable<T> {

        private IForkCommand command;
        private IForkProtocolProcessor processor;
        private boolean waitResponse;

        public CommandExecutionCallable(IForkCommand command,
                                        IForkProtocolProcessor processor,
                                        boolean waitResponse) {
            Commons.checkNotNull(command);
            Commons.checkNotNull(processor);

            this.waitResponse = waitResponse;
            this.command = command;
            this.processor = processor;
        }

        @Override
        public T call() throws Exception {
            if ( command.getQualifier() == null ) {
                command.setQualifier( new Date().getTime() );
            }

            this.processor.getWorkQueue().collectRequest(command);

            if ( !waitResponse ) {
                return null;
            }

            T result;
            do {
                result = (T) this.processor.getWorkQueue().peekResponse(new IFilter<IForkCommandResponse>() {
                    @Override
                    public boolean filter(IForkCommandResponse filterable) {
                        return filterable.getQualifier().equals(command.getQualifier());
                    }
                });

                if ( result == null ) {
                    log.info("Waiting for response...");
                    try {
                        Thread.sleep(250);
                    } catch ( InterruptedException e ) {}
                }
            } while ( result == null );

            log.info("Response received: " + result.getClass().getCanonicalName() );

            return result;
        }
    }

    public enum State {
        INIT,
        START,
        STOP
    }

    private State state;
    private IForkProtocolProcessor processor;
    private ExecutorService service;
    private Collection<IForkCommandHandler> handlers = new ArrayList<IForkCommandHandler>();

    public StandardForkCommandExecutor( IForkProtocolProcessor processor,
                                        Collection<IForkCommandHandler> handlers) {
        Commons.checkNotNull(processor);

        this.service = this.createExecutorService();
        this.processor = processor;
        this.state = State.STOP;
        this.handlers = handlers;
    }

    protected ExecutorService createExecutorService() {
        return Executors.newFixedThreadPool(10);
    }

    @Override
    public void addHandler(IForkCommandHandler handler) {
        this.handlers.add(handler);
    }

    @Override
    public void removeHandler(IForkCommandHandler handler) {
        this.handlers.remove(handler);
    }

    @Override
    public void clearHandlers() {
        this.handlers.clear();
    }

    @Override
    public boolean isStarted() {
        return this.state.equals( State.START );
    }

    @Override
    public void run() {
        try {
            this.start();
        } catch ( ProcessException e ) {
            throw new IllegalStateException( e.getMessage(), e );
        }
    }

    @Override
    public void start() throws ProcessException {
        this.state = State.START;

        this.raiseEvent( new ExecutorStartedEvent() );

        try {
            Collection<Future<Void>> listeners = this.service.invokeAll(
                    Commons.set( new CommandsListenerThread(), new ResponsesListenerThread() )
            );

            for ( Future<?> future : listeners ) {
                future.get();
            }
        } catch ( ExecutionException e ) {
             throw new ProcessException( e.getMessage(), e );
        } catch ( InterruptedException e ) {
            throw new ProcessException( "Interrupted", e );
        }
    }

    protected IForkCommandResponse executeCommand( IForkCommand command ) throws ProcessException {
        Commons.checkNotNull(command);

        IForkCommandHandler targetHandler = null;
        for ( IForkCommandHandler handler : this.handlers ) {
            if ( handler.isSupported(command) ) {
                targetHandler = handler;
                break;
            }
        }

        if ( targetHandler == null ) {
            return null;
        }

        return targetHandler.execute(command);
    }

    @Override
    public void respond(IForkCommandResponse response) throws ProcessException {
        this.processor.getWorkQueue().collectResponse(response);
    }

    protected <T extends IForkCommandResponse> Callable<T> createExecuteCallable( IForkCommand command,
                                                                                  boolean waitResponse ) {
        return new CommandExecutionCallable(command, this.processor, waitResponse);
    }

    @Override
    public void executeAsync(IForkCommand command) throws ProcessException {
        try {
            this.service.submit( this.createExecuteCallable(command, false) ).get();
        } catch ( ExecutionException e ) {
            throw new ProcessException( e.getMessage(), e );
        } catch ( InterruptedException e ) {
            throw new ProcessException( "Interrupted", e );
        }
    }

    @Override
    public <T extends IForkCommandResponse> T execute(IForkCommand command) throws ProcessException {
        try {
            Future<T> result = this.service.submit( this.<T>createExecuteCallable(command, true) );
            return result.get();
        } catch ( ExecutionException e ) {
            throw new ProcessException( e.getMessage(), e );
        } catch ( InterruptedException e ) {
            throw new ProcessException("Interrupted", e );
        }
    }

    @Override
    public void stop() {
        this.state = State.STOP;
    }
}
