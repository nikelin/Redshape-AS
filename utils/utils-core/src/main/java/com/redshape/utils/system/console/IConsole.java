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

package com.redshape.utils.system.console;

import com.redshape.utils.system.scripts.IScriptExecutor;
import com.redshape.utils.system.scripts.IScriptListExecutor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author nikelin
 *
 * @TODO: add cleaning up interface for cached executors
 */
public interface IConsole {
    
    public boolean isDirectory( String path ) throws IOException;
    
    public void mkdir( String path ) throws IOException;
    
	public void deleteFile( String path ) throws IOException;

	public boolean checkExists( String path ) throws IOException;

	public String readFile( String path ) throws IOException;

	public InputStream openReadStream( String path ) throws IOException;

	public OutputStream openWriteStream( String path ) throws IOException;

	public void stopScripts( Object context );

    public IScriptExecutor createExecutor( String command );

    public IScriptExecutor createExecutor( Object context, String command );

    public <T extends IScriptExecutor> IScriptListExecutor<T> createListExecutor();

    public <T extends IScriptExecutor> IScriptListExecutor<T> createListExecutor( Object context );

}