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

package com.redshape.utils.system.processes;

/**
 * @author nikelin
 * @date 20:27
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class SystemProcess implements ISystemProcess{
	private static final long serialVersionUID = -4160019280880543328L;

	private String errorBuff;
	private String inputBuff;
    private Process process;

    public SystemProcess(Process process) {
        this.process = process;
    }

    @Override
    public boolean isSuccessful() throws IOException {
    	return ( this.readStdError() == null )
    				&& this.exitValue() != 0;
    }

    @Override
    /**
     * @TODO test firstly
     */
    public synchronized String readStdInput() throws IOException {
    	if ( this.inputBuff != null ) {
    		return this.inputBuff;
    	}

    	BufferedReader reader = new BufferedReader( new InputStreamReader( this.getInputStream() ) );
    	StringBuilder builder = new StringBuilder();
    	String buff;
    	while( null != ( buff = reader.readLine() ) ) {
    		builder.append( buff ).append("\n");
    	}

    	return this.inputBuff = builder.toString();
    }

    @Override
    /**
     * @TODO test firstly
     */
    public synchronized String readStdError() throws IOException {
    	if ( this.errorBuff != null ) {
    		return this.errorBuff;
    	}

    	BufferedReader reader = new BufferedReader( new InputStreamReader( this.getErrorStream() ) );
    	StringBuilder builder = new StringBuilder();
    	String buff;
    	while( null != ( buff = reader.readLine() ) ) {
    		builder.append( buff ).append("\n");
    	}

    	return this.errorBuff = builder.toString();
    }

    public OutputStream getOutputStream() {
        return this.process.getOutputStream();
    }

    public InputStream getInputStream() {
        return process.getInputStream();
    }

    public InputStream getErrorStream() {
        return process.getErrorStream();
    }

    @Override
    public int waitFor() throws InterruptedException {
        return process.waitFor();
    }

    @Override
    public int exitValue() {
        return process.exitValue();
    }

    @Override
    public void destroy() {
        process.destroy();
    }

    //TODO: implement PID detection
    @Override
    public int getPID() {
        return 0;
    }
}

