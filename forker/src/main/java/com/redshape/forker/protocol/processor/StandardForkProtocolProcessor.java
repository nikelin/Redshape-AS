package com.redshape.forker.protocol.processor;

import com.redshape.forker.IForkCommand;
import com.redshape.forker.IForkCommandResponse;
import com.redshape.forker.protocol.IForkProtocol;
import com.redshape.forker.protocol.queue.IProtocolQueue;
import com.redshape.utils.Commons;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/29/12
 * Time: 5:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class StandardForkProtocolProcessor implements IForkProtocolProcessor {

    private static final Logger log = Logger.getLogger(StandardForkProtocolProcessor.class);

    private ScheduledExecutorService threadsExecutor;

    private IProtocolQueue workQueue;
    private IProtocolQueue resultsQueue;
    private IForkProtocol protocol;

    private int commandWriterDelay;
    private int responseWriterDelay;
    private int writerWorkChunk;
    private boolean running;

    private static final int DEFAULT_WRITER_DELAY = 250;
    private static final int DEFAULT_WORK_CHUNK = 50;

    public StandardForkProtocolProcessor(IProtocolQueue workQueue,
                                         IProtocolQueue resultsQueue,
                                         IForkProtocol protocol) {
        this(workQueue, resultsQueue, protocol,
                DEFAULT_WRITER_DELAY,
                DEFAULT_WRITER_DELAY,
                DEFAULT_WORK_CHUNK );
    }

    public StandardForkProtocolProcessor(IProtocolQueue workQueue,
                                         IProtocolQueue resultsQueue,
                                         IForkProtocol protocol,
                                         int commandWriterDelay,
                                         int responseWriterDelay,
                                         int writerWorkChunk) {
        Commons.checkNotNull( resultsQueue );
        Commons.checkNotNull( workQueue );
        Commons.checkNotNull( protocol );
        Commons.checkArgument(commandWriterDelay > 0);
        Commons.checkArgument( responseWriterDelay > 0 );
        Commons.checkArgument( writerWorkChunk > 0 );

        this.protocol = protocol;
        this.workQueue = workQueue;
        this.resultsQueue = resultsQueue;
        this.protocol = protocol;
        this.commandWriterDelay = commandWriterDelay;
        this.responseWriterDelay = responseWriterDelay;
        this.writerWorkChunk = writerWorkChunk;
        this.threadsExecutor = this.createExecutor();
    }

    @Override
    public IProtocolQueue getWorkQueue() {
        return workQueue;
    }

    @Override
    public IProtocolQueue getResultsQueue() {
        return resultsQueue;
    }

    protected ScheduledExecutorService createExecutor() {
        return Executors.newScheduledThreadPool(5);
    }

    protected IForkProtocol getProtocol() {
        return protocol;
    }

    protected void onResponse() throws IOException {
        this.resultsQueue.collectResponse(this.getProtocol().readResponse());
    }

    protected void onCommand() throws IOException {
        this.resultsQueue.collectRequest(this.getProtocol().readCommand());
    }

    protected Runnable createResultsCollectorThread() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    IForkProtocol.TokenType type = getProtocol().matchToken();
                    if ( type == null ) {
                        return;
                    }

                    switch ( type ) {
                        case COMMAND:
                            onCommand();
                            break;
                        case RESPONSE:
                            onResponse();
                            break;
                    }
                } catch ( IOException e ) {
                    throw new IllegalStateException( e.getMessage(), e );
                }
            }
        };
    }

    protected Runnable createCommandWriterThread() {
        return new Runnable() {
            @Override
            public void run() {
                int count = 0;

                if ( !workQueue.hasRequest() ) {
                    return;
                }

                while ( count < writerWorkChunk ) {
                    IForkCommand command = workQueue.peekRequest();
                    if ( command == null ) {
                        continue;
                    }

                    try {
                        getProtocol().writeCommand(command);
                    } catch ( IOException e ) {
                        log.error(e.getMessage(), e);
                    }

                    count += 1;
                }
            }
        };
    }


    protected Runnable createResponseWriterThread() {
        return new Runnable() {
            @Override
            public void run() {
                int count = 0;
                if ( !workQueue.hasResponse() ) {
                    return;
                }

                while ( count < writerWorkChunk ) {
                    IForkCommandResponse response = workQueue.peekResponse();
                    if ( response == null ) {
                        continue;
                    }

                    try {
                        getProtocol().writeResponse(response);
                    } catch ( IOException e ) {
                        log.error( e.getMessage(), e );
                    }

                    count += 1;
                }
            }
        };
    }

    @Override
    public boolean isStarted() {
        return this.running;
    }

    @Override
    public void stop() {
        this.running = false;
        this.threadsExecutor.shutdown();
    }

    @Override
    public void run() {
        this.threadsExecutor.scheduleAtFixedRate(this.createResultsCollectorThread(), 0,
                400, TimeUnit.MILLISECONDS);
        this.threadsExecutor.scheduleAtFixedRate(this.createResponseWriterThread(), this.responseWriterDelay,
                this.responseWriterDelay, TimeUnit.MILLISECONDS );
        this.threadsExecutor.scheduleAtFixedRate(this.createCommandWriterThread(), this.commandWriterDelay,
                this.commandWriterDelay, TimeUnit.MILLISECONDS );

        try {
            while ( this.isStarted() ) {
                Thread.sleep(5000);
            }
        } catch ( InterruptedException e ) {}
    }
}
