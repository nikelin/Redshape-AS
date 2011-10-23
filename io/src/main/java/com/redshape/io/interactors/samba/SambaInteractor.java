package com.redshape.io.interactors.samba;

import com.redshape.io.IFilesystemNode;
import com.redshape.io.INetworkInteractor;
import com.redshape.io.interactors.SambaConnection;
import com.redshape.utils.system.scripts.IScriptExecutor;

import java.io.IOException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.io.interactors.samba
 * @date 10/22/11 9:30 PM
 */
public class SambaInteractor implements INetworkInteractor {
	private SambaConnection connection;

	public SambaInteractor( SambaConnection connection ) {
		this.connection = connection;
	}

	@Override
	public void execute(IScriptExecutor executor) throws IOException {
		throw new UnsupportedOperationException("Needs to be implemented");
	}

	@Override
	public IFilesystemNode getRoot() throws IOException {
		return this.connection.getFile("/");
	}

	@Override
	public IFilesystemNode getFile(String name) throws IOException {
		return this.connection.getFile(name);
	}
}
