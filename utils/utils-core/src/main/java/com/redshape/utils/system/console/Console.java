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
import com.redshape.utils.system.scripts.bash.BashScriptExecutor;
import com.redshape.utils.system.scripts.bash.BashScriptListExecutor;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author nikelin
 */
public class Console implements IConsole {
	private Map<Object, Collection<IScriptExecutor>> executors = new HashMap<Object, Collection<IScriptExecutor>>();
    private ConsoleCommandGenerator consoleCommandGenerator = ConsoleCommandGeneratorFactory.createConsoleCommandGenerator();
	/**
	 * For testability purpouses
	 * @param command
	 * @return
	 */
	protected IScriptExecutor createExecutorObject( String command ) {
		return new BashScriptExecutor(command);
	}

    @Override
    public boolean isDirectory(String path) throws IOException {
        return this.<File>provideFile(path, false).isDirectory();
    }

    @Override
    public void mkdir(String path) throws IOException {
        if ( checkExists(path) )
            return;

        String command = consoleCommandGenerator.generateCreateDirCommand(path);

        // todo: synchronize on path?
        executeCommand(path, command);


    }

    @Override
	public void deleteFile(String path) throws IOException {
        if ( !checkExists(path) )
            return;

        String command = consoleCommandGenerator.generateDeleteDirCommand(path);

        // todo: synchronize on path?
        executeCommand(path, command);
    }

    private String executeCommand(Object context, String command) throws IOException {
        return createExecutor(context, command).execute();
    }

	@Override
	public boolean checkExists(String path) throws IOException {
		return this.<File>provideFile(path, false).exists();
	}

	/**
	 * For testability purpouses
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T extends IScriptExecutor> IScriptListExecutor<T> createListExecutorObject() {
		return (IScriptListExecutor<T>) new BashScriptListExecutor();
	}

	@Override
	public IScriptExecutor createExecutor(String command) {
		return this.createExecutor(this, command);
	}

	@Override
	public <T extends IScriptExecutor> IScriptListExecutor<T> createListExecutor() {
		return this.createListExecutor(this);
	}

	@Override
	public void stopScripts(Object context) {
		Collection<IScriptExecutor> executors = this.executors.get(context);
		if ( executors == null ) {
			return;
		}

		for ( IScriptExecutor executor : executors ) {
			executor.kill();
		}
	}

	@Override
	public String readFile(String path) throws IOException {
		StringBuilder builder = new StringBuilder();
		BufferedReader bufferedStream = new BufferedReader(
			new InputStreamReader( this.openReadStream(path) )
		);

		String tmp;
		while ( null != ( tmp = bufferedStream.readLine() ) ) {
			builder.append(tmp).append("\n");
		}

		return builder.toString();
	}

	protected <T> T provideFile( String path, boolean createIfNotExists ) throws IOException {
		File file = new File(path);
		if ( createIfNotExists ) {
			file.createNewFile();
		}

		return (T) file;
	}

	@Override
	public InputStream openReadStream(String path) throws IOException {
		return new FileInputStream( this.<File>provideFile(path, false) );
	}

	@Override
	public OutputStream openWriteStream(String path) throws IOException {
		return new FileOutputStream( this.<File>provideFile(path, true) );
	}

	@Override
	public IScriptExecutor createExecutor(Object context, String command) {
		IScriptExecutor executor = this.createExecutorObject(command);

		this.registerExecutor( context, executor );

		return executor;
	}

	@Override
	public <T extends IScriptExecutor> IScriptListExecutor<T>
						createListExecutor( Object context) {
		IScriptListExecutor<T> executor = this.createListExecutorObject();

		this.registerExecutor( context, executor );

		return executor;
	}

	protected void registerExecutor( Object context, IScriptExecutor executor ) {
		if ( this.executors.get(context) == null ) {
			this.executors.put( context, new HashSet<IScriptExecutor>() );
		}

		this.executors.get(context).add( executor );
	}

}