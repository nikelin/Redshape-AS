package com.redshape.io.interactors.ssh.console;

import com.redshape.io.*;
import com.redshape.io.interactors.ServiceID;
import com.redshape.io.interactors.ssh.SSHFile;
import com.redshape.utils.system.console.Console;
import com.redshape.utils.system.scripts.IScriptExecutor;
import com.redshape.utils.system.scripts.IScriptListExecutor;
import com.redshape.utils.system.scripts.bash.BashScriptExecutor;
import com.redshape.utils.system.scripts.bash.BashScriptListExecutor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.io.interactors.console
 * @date 10/22/11 10:09 PM
 */
public class RemoteConsole extends Console {
	@Autowired( required = true )
	private IInteractorsFactory interactorsFactory;

	private INetworkNode node;
	private INetworkInteractor interactor;

	public RemoteConsole( INetworkNode node ) {
		this.node = node;
	}

	public IFilesystemNode getFile( String path ) throws IOException {
		return this.getFile(path, true);
	}

	public IFilesystemNode getFile( String path, boolean createIfNotExists ) throws IOException {
		IFilesystemNode node = this.getInteractor().getFile(path);
		if ( ( node == null || !node.isExists() ) && createIfNotExists ) {
			node = this.getInteractor().createFile(path);
		}

		return node;
	}

	public IInteractorsFactory getInteractorsFactory() {
		return interactorsFactory;
	}

	public void setInteractorsFactory(IInteractorsFactory interactorsFactory) {
		this.interactorsFactory = interactorsFactory;
	}

	protected INetworkInteractor getInteractor() throws IOException {
		if ( this.interactor == null
				|| !this.isConnected() ) {
			this.connect();
		}

		return this.interactor;
	}

	protected synchronized void connect() throws IOException {
		if ( this.interactor != null ) {
			return;
		}

		try {
			INetworkConnection<SSHFile> connection = this.getInteractorsFactory().findInteractor(ServiceID.SSH, node);
			connection.connect();

			this.interactor = connection.createInteractor();
		} catch ( InstantiationException e ) {
			throw new IOException( e.getMessage(), e );
		}
	}

	/**
	 * @FIXME: Implement
	 * @return
	 */
	protected boolean isConnected() {
		return true;
	}

	@Override
	public void deleteFile(String path) throws IOException {
		IFilesystemNode node = this.provideFile(path, false);
		if ( !node.isExists() ) {
			return;
		}

		node.remove();
	}

	@Override
	public boolean checkExists(String path) throws IOException {
		IFilesystemNode node = this.<IFilesystemNode>provideFile(path, false);

		return node != null && node.isExists();
	}

	@Override
	protected IScriptExecutor createExecutorObject(String command) {
		return new BashScriptExecutor() {
			@Override
			public String execute() throws IOException {
				RemoteConsole.this.getInteractor().execute( this );
				return this.getExecutionResult();
			}
		};
	}

	@Override
	protected <T> T provideFile(String path, boolean createIfNotExists) throws IOException {
		IFilesystemNode node = this.getFile(path, createIfNotExists);
		if ( createIfNotExists && ( null == node || !node.isExists() ) ) {
			node = this.getFile(path, true);
		}

		return (T) node;
	}

	@Override
	public InputStream openReadStream(String path) throws IOException {
		IFilesystemNode node = this.<IFilesystemNode>provideFile(path, false);
        if ( node == null ) {
            return null;
        }

        return node.getInputStream();
	}

	@Override
	public OutputStream openWriteStream(String path) throws IOException {
		IFilesystemNode node = this.<IFilesystemNode>provideFile(path, true);
        if ( node == null ) {
            return null;
        }

        return node.getOutputStream();
	}

	@Override
	protected IScriptListExecutor<BashScriptExecutor> createListExecutorObject() {
		return new BashScriptListExecutor() {
			@Override
			public String execute() throws IOException {
				RemoteConsole.this.getInteractor().execute( this );
				return this.getExecutionResult();
			}
		};
	}
}
