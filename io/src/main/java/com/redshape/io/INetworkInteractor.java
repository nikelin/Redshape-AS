package com.redshape.io;

import com.redshape.utils.system.scripts.IScriptExecutor;

import java.io.IOException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.io
 * @date 10/22/11 9:23 PM
 */
public interface INetworkInteractor {

	public void execute( IScriptExecutor executor ) throws IOException;

	public IFilesystemNode getRoot() throws IOException;

	public IFilesystemNode getFile( String name ) throws IOException;

}
