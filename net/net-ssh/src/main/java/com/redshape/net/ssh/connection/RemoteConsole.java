package com.redshape.net.ssh.connection;

import com.redshape.net.IServer;
import com.redshape.net.connection.ConnectionException;
import com.redshape.net.connection.IServerConnection;
import com.redshape.net.connection.IServerConnectionFactory;
import com.redshape.net.connection.capabilities.IConsoleCapabilitySupport;
import com.redshape.net.connection.capabilities.IFilesystemCapabilitySupport;
import com.redshape.net.connection.capabilities.IServerCapabilitySupportFactory;
import com.redshape.utils.system.console.Console;
import com.redshape.utils.system.scripts.IScriptExecutor;
import com.redshape.utils.system.scripts.IScriptListExecutor;
import com.redshape.utils.system.scripts.bash.BashScriptExecutor;
import com.redshape.utils.system.scripts.bash.BashScriptListExecutor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RemoteConsole extends Console {
    private IServerConnectionFactory serverConnectionFactory;
    private IServerCapabilitySupportFactory serverCapabilitySupportFactory;

    private IServer node;
	private IServerConnection connection;

	public RemoteConsole( IServer node,
                          IServerConnectionFactory connectionFactory,
                          IServerCapabilitySupportFactory capabilitySupportFactory ) {
		this.node = node;
        this.serverConnectionFactory = connectionFactory;
        this.serverCapabilitySupportFactory = capabilitySupportFactory;
	}

    public void setServerConnectionFactory(IServerConnectionFactory serverConnectionFactory) {
        this.serverConnectionFactory = serverConnectionFactory;
    }

    public IServerCapabilitySupportFactory getServerCapabilitySupportFactory() {
        return serverCapabilitySupportFactory;
    }

    public void setServerCapabilitySupportFactory(IServerCapabilitySupportFactory serverCapabilitySupportFactory) {
        this.serverCapabilitySupportFactory = serverCapabilitySupportFactory;
    }

    public IServerConnectionFactory getServerConnectionFactory() {
        return serverConnectionFactory;
    }

    public IServer getNode() {
        return node;
    }

    public IFilesystemCapabilitySupport.Node getFile( String path ) throws IOException {
		return this.getFile(path, true);
	}

    protected IFilesystemCapabilitySupport getFilesystem() throws IOException {
        return this.getServerCapabilitySupportFactory().getFilesystemCapability(this.getInteractor());
    }

    protected IConsoleCapabilitySupport getConsole() throws IOException {
        return this.getServerCapabilitySupportFactory().getConsoleCapability(this.getInteractor());
    }

	public IFilesystemCapabilitySupport.Node getFile( String path, boolean createIfNotExists ) 
            throws IOException {
        try {
            IFilesystemCapabilitySupport.Node node = null;
            try {
                node = this.getFilesystem().findFile(path);
            } catch ( ConnectionException e ) {
                if ( createIfNotExists ) {
                    node = this.getFilesystem().createFile(path);
                }
            }

            return node;
        } catch ( ConnectionException e ) {
            throw new IOException( e.getMessage(), e );
        }
	}

	protected IServerConnection getInteractor() throws IOException {
		if ( this.connection == null
				|| !this.isConnected() ) {
			this.connect();
		}

		return this.connection;
	}

	protected synchronized void connect() throws IOException {
		if ( this.connection != null ) {
			return;
		}

		try {
			IServerConnection connection = this.getServerConnectionFactory().createConnection(this.getNode());

			connection.connect();
            
            this.connection = connection;
		} catch ( ConnectionException e ) {
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
        try {
            IFilesystemCapabilitySupport.Node node = this.provideFile(path, false);
            if ( !node.isExists() ) {
                return;
            }
    
            node.delete();
        } catch ( ConnectionException e ) {
            throw new IOException( e.getMessage(), e );
        }
	}

	@Override
	public boolean checkExists(String path) throws IOException {
        try {
            IFilesystemCapabilitySupport.Node node = this.provideFile(path, false);
    
            return node != null && node.isExists();
        } catch ( ConnectionException e ) {
            throw new IOException( e.getMessage(), e );
        }
	}

	@Override
	protected IScriptExecutor createExecutorObject(String command) {
        return new BashScriptExecutor() {
            @Override
            public String execute() throws IOException {
                try {
                    RemoteConsole.this.getConsole().execute(this);
                    return this.getExecutionResult();
                } catch ( ConnectionException e ) {
                    throw new IOException( e.getMessage(), e );
                }
            }
        };
	}

	@Override
	protected <T> T provideFile(String path, boolean createIfNotExists) throws IOException {
        try {
            IFilesystemCapabilitySupport.Node node = this.getFile(path, createIfNotExists);
            if ( createIfNotExists && ( null == node || !node.isExists() ) ) {
                node = this.getFile(path, true);
            }
    
            return (T) node;
        } catch ( ConnectionException e ) {
            throw new IOException( e.getMessage(), e );
        }
	}

	@Override
	public InputStream openReadStream(String path) throws IOException {
		IFilesystemCapabilitySupport.Node node = this.provideFile(path, false);
        if ( node == null ) {
            return null;
        }

        return node.getInputStream();
	}

	@Override
	public OutputStream openWriteStream(String path) throws IOException {
		IFilesystemCapabilitySupport.Node node = this.provideFile(path, true);
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
                try {
                    RemoteConsole.this.getConsole().execute( this );
                    return this.getExecutionResult();
                } catch ( ConnectionException e ) {
                    throw new IOException( e.getMessage(), e );
                }
			}
		};
	}
}