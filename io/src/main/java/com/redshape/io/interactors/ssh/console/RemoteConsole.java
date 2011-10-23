package com.redshape.io.interactors.ssh.console;

import com.redshape.io.IInteractorsFactory;
import com.redshape.io.INetworkConnection;
import com.redshape.io.INetworkInteractor;
import com.redshape.io.INetworkNode;
import com.redshape.io.interactors.ServiceID;
import com.redshape.io.interactors.ssh.SSHFile;
import com.redshape.utils.system.console.Console;
import com.redshape.utils.system.scripts.IScriptExecutor;
import com.redshape.utils.system.scripts.IScriptListExecutor;
import com.redshape.utils.system.scripts.bash.BashScriptExecutor;
import com.redshape.utils.system.scripts.bash.BashScriptListExecutor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

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
