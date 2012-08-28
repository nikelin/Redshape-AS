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
        }

        @Override
        public void onClosed(InputStream stream) {
        }
    }

    private ISystemProcess process;
    private IResourcesLoader loader;
    private DataOutputStream output;
    private DataInputStream input;
    private IForkManager manager;

    private IStreamWaiter streamWaiter;

    public ForkImpl( IForkManager manager, IResourcesLoader loader, ISystemProcess process ) {
        super();
        Commons.checkNotNull(process);
        Commons.checkNotNull(manager);
        Commons.checkNotNull(loader);

        this.loader = loader;
        this.manager = manager;
        this.process = process;
        this.input = new DataInputStream( process.getInputStream() );
        this.output = new DataOutputStream( process.getOutputStream() );
        this.streamWaiter = this.createStreamWaiter( this.process.getInputStream() );
        this.streamWaiter.addEventHandler( new ResponseReader() );
    }

    @Override
    public DataInputStream getInput() {
        return this.input;
    }

    @Override
    public DataOutputStream getOutput() {
        return this.output;
    }

    @Override
    public int getPID() {
        return this.process.getPID();
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
        GetRunningStateCommand.Response response = this.manager.getExecutor()
                .execute(new GetRunningStateCommand.Request());
        if ( response == null ) {
            throw new ProcessException("<NULL>");
        }

        return response.getState();
    }

    @Override
    public void resume() throws ProcessException {
        IForkCommandResponse response = this.manager.getExecutor().execute( new ResumeCommand.Request() );
        this.assertSuccess(response, "Unable to resume process");
    }

    @Override
    public void shutdown() throws ProcessException {
        try {
            this.manager.getExecutor().execute(new ShutdownCommand.Request());
        } finally {
            this.getProcess().destroy();
        }
    }

    @Override
    public void pause() throws ProcessException {
        this.assertSuccess(this.manager.getExecutor().execute(new PauseCommand.Request()), "Failed to pause process");
    }
    
    private void assertSuccess( IForkCommandResponse response, String message )
        throws ProcessException {
        if ( !response.getStatus().equals( IForkCommandResponse.Status.SUCCESS ) ) {
            throw new ProcessException( message );
        }
    }
}
