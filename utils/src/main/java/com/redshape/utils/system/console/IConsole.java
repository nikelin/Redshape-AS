package com.redshape.utils.system.console;

import com.redshape.utils.system.scripts.IScriptExecutor;
import com.redshape.utils.system.scripts.IScriptListExecutor;

/**
 * @author nikelin
 *
 * @TODO: add cleaning up interface for cached executors
 */
public interface IConsole {

	public void stopScripts( Object context );

    public IScriptExecutor createExecutor( String command );

    public IScriptExecutor
    	createExecutor( Object context, String command );

    public <T extends IScriptExecutor> IScriptListExecutor<T>
    	createListExecutor();

    public <T extends IScriptExecutor> IScriptListExecutor<T>
    	createListExecutor( Object context );

}