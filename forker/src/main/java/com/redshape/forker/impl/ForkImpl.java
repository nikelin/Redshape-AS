/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.forker.impl;

import com.redshape.forker.*;
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
        throw new UnsupportedOperationException("Not supported now");
    }

    @Override
    public void resume() throws ProcessException {
        throw new UnsupportedOperationException("Not supported now");
    }

    @Override
    public void shutdown() throws ProcessException {
        this.process.destroy();
    }

    @Override
    public void pause() throws ProcessException {
        throw new UnsupportedOperationException("Not supported now");
    }
    
    private void assertSuccess( IForkCommandResponse response, String message )
        throws ProcessException {
        if ( !response.getStatus().equals( IForkCommandResponse.Status.SUCCESS ) ) {
            throw new ProcessException( message );
        }
    }
}
