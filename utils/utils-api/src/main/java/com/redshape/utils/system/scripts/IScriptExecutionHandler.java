package com.redshape.utils.system.scripts;

import com.redshape.utils.system.processes.ISystemProcess;

import java.io.IOException;

/**
 * @author nikelin
 * @date 20:25
 */
public interface IScriptExecutionHandler {

	public Integer onProgressRequested( ISystemProcess process ) throws IOException;

	public boolean onExit( String output );

	public void onError( String output );

}
