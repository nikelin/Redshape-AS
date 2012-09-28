package com.redshape.net.connection.capabilities;

import com.redshape.net.connection.ConnectionException;
import com.redshape.utils.system.scripts.IScriptExecutor;

/**
 * @author nikelin
 * @date 16:09
 */
public interface IConsoleCapabilitySupport extends IServerCapabilitySupport {

    public void execute( IScriptExecutor executor ) throws ConnectionException;

}
