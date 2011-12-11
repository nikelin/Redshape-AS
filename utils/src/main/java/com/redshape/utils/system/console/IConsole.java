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