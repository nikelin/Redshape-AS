package com.redshape.forker.impl;

import com.redshape.forker.*;
import com.redshape.forker.commands.GetRunningStateCommand;
import com.redshape.forker.commands.PauseCommand;
import com.redshape.forker.commands.ResumeCommand;
import com.redshape.forker.commands.ShutdownCommand;
import com.redshape.utils.Commons;
import com.redshape.utils.IResourcesLoader;
import com.redshape.utils.streams.IStreamEventHandler;
import com.redshape.utils.streams.IStreamWaiter;
import com.redshape.utils.streams.StreamWaiter;
import com.redshape.utils.system.processes.ISystemProcess;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @package com.redshape.forker.impl
 * @date 1/31/12 {3:35 PM}
 */
public class ForkImpl implements IFork {
    
    protected class ResponseReader implements IStreamEventHandler {

        @Override
        public void onData(InputStream stream) throws IOException {
            DataInputStream reader = new DataInputStream(stream);

            IForkCommandResponse response;
            try {
                response = Commands.createResponse(reader.readLong(), IForkCommandResponse.Status.valueOf( reader.readUTF() ) );
            } catch ( Throwable e ) {
                throw new IOException("Unable to read response object");
            }

            response.readFrom(reader);

            ForkImpl.this.lastResponse = response;
        }

        @Override
        public void onClosed(InputStream stream) {
        }
    }

    public static final long COMMAND_BEGIN = 0x100001L;
    public static final long COMMAND_END = 0x200002L;

    private ISystemProcess process;
    private IResourcesLoader loader;
    private DataOutputStream output;

    private IForkCommandResponse lastResponse;

    private IStreamWaiter streamWaiter;
    
    private Object executionLock = new Object();

    public ForkImpl(IResourcesLoader loader, ISystemProcess process) {
        super();

        Commons.checkNotNull(loader);
        this.loader = loader;

        Commons.checkNotNull(process);
        this.process = process;
        this.output = new DataOutputStream( process.getOutputStream() );
        this.streamWaiter = this.createStreamWaiter( this.process.getInputStream() );
        this.streamWaiter.addEventHandler( new ResponseReader() );
    }

    protected void setLastResponse( IForkCommandResponse response ) {
        this.lastResponse = response;
    }

    protected IStreamWaiter getStreamWaiter() {
        return this.streamWaiter;
    }

    protected IStreamWaiter createStreamWaiter( InputStream stream ) {
        return new StreamWaiter( stream );
    }
    
    protected IResourcesLoader getLoader() {
        return this.loader;
    }

    protected ISystemProcess getProcess() {
        return this.process;
    }

    @Override
    public boolean isPaused() throws ProcessException {
        return this.<GetRunningStateCommand.Response>submit(
            new GetRunningStateCommand.Request()
        )
        .getState();
    }

    @Override
    public void resume() throws ProcessException {
        this.assertSuccess(this.submit(new ResumeCommand.Request()), "Unable to resume process");
    }

    @Override
    public void shutdown() throws ProcessException {
        this.submit( new ShutdownCommand.Request() );
        this.getProcess().destroy();
    }

    @Override
    public void pause() throws ProcessException {
        this.assertSuccess(this.submit(new PauseCommand.Request()), "Failed to pause process");
    }

    @Override
    public <T extends IForkCommandResponse> T submit(IForkCommand command) 
            throws ProcessException {
        try {
            synchronized (this.executionLock) {
                this.output.writeLong( COMMAND_BEGIN );
                this.output.writeLong( command.getCommandId() );
                command.writeTo(this.output);
                this.output.writeLong( COMMAND_END );
    
                this.getStreamWaiter().await();

                return (T) this.lastResponse;
            }
        } catch ( IOException e ) {
            throw new ProcessException( e.getMessage(), e );
        }
    }
    
    private void assertSuccess( IForkCommandResponse response, String message )
        throws ProcessException {
        if ( !response.getStatus().equals( IForkCommandResponse.Status.SUCCESS ) ) {
            throw new ProcessException( message );
        }
    }
}
