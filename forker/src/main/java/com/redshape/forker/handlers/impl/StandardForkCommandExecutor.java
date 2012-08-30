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

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/27/12
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class StandardForkCommandExecutor extends AbstractEventDispatcher implements IForkCommandExecutor, Runnable {

    private static final Logger log = Logger.getLogger(StandardForkCommandExecutor.class);

    public enum State {
        INIT,
        START,
        STOP
    }

    private State state;
    private IForkProtocolProcessor processor;
    private Collection<IForkCommandHandler> handlers = new ArrayList<IForkCommandHandler>();

    public StandardForkCommandExecutor( IForkProtocolProcessor processor,
                                        Collection<IForkCommandHandler> handlers) {
        Commons.checkNotNull(processor);

        this.processor = processor;
        this.state = State.STOP;
        this.handlers = handlers;
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

        while ( this.isStarted() ) {
            IForkCommand command = this.processor.getResultsQueue().peekRequest();
            if ( command == null ) {
                continue;
            }

            this.raiseEvent( new CommandRequestEvent(command) );

            IForkCommandResponse response = this.executeCommand(command);
            if ( response == null ) {
                response = new ErrorResponse("Unsupported command requested", IForkCommandResponse.Status.FAIL);
            }

            this.raiseEvent( new CommandResponseEvent(response) );

            this.processor.getResultsQueue().collectResponse(response);
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

    @Override
    public <T extends IForkCommandResponse> T execute(final IForkCommand command) throws ProcessException {
        if ( command.getQualifier() == null ) {
            command.setQualifier( new Date().getTime() );
        }

        this.processor.getWorkQueue().collectRequest(command);

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

    @Override
    public void stop() {
        this.state = State.STOP;
    }
}
