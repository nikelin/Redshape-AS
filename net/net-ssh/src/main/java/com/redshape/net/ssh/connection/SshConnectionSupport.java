package com.redshape.net.ssh.connection;

import com.redshape.net.IServer;
import com.redshape.net.connection.ConnectionAttribute;
import com.redshape.net.connection.ConnectionException;
import com.redshape.net.connection.IServerConnection;
import com.redshape.net.connection.auth.IConnectionAuthenticator;
import com.redshape.net.ssh.connection.auth.ISshAuthenticator;
import com.redshape.utils.Commons;
import net.schmizz.sshj.SSHClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nikelin
 * @date 13:25
 */
public class SshConnectionSupport implements IServerConnection {

    private IServer server;
    private SSHClient connection;
    private IConnectionAuthenticator authenticator;
    private Map<ConnectionAttribute, Object> attributes = new HashMap<ConnectionAttribute, Object>();

    public SshConnectionSupport( IServer server, ISshAuthenticator authenticator ) {
        Commons.checkNotNull(server);
        Commons.checkNotNull(authenticator);

        this.server = server;
        this.authenticator = authenticator;
    }

    protected IServer getServer() {
        return this.server;
    }
    
    protected IConnectionAuthenticator getAuthenticator() {
        return authenticator;
    }

    protected SSHClient getConnection() {
        return this.connection;
    }

    @Override
    public void connect() throws ConnectionException {
        try {
            if ( this.isConnected() ) {
                this.disconnect();
            }

            this.connection = new SSHClient();
            this.connection.setConnectTimeout(
                Commons.select(this.<Integer>getAttribute(ConnectionAttribute.TIMEOUT), 30000)
            );
            
            this.connection.connect( server.toURI().toString(), 22 );

            this.getAuthenticator().authenticate( this.getServer(), this.getConnection() );
        } catch ( Throwable e ) {
            throw new ConnectionException( e.getMessage(), e );
        }
    }

    @Override
    public boolean isConnected() {
        return null != this.getConnection() && this.getConnection().isConnected();
    }

    @Override
    public void disconnect() throws ConnectionException {
        try {
            if ( this.connection != null && this.connection.isConnected() ) {
                this.connection.disconnect();
            }
        } catch ( IOException e ) {
            throw new ConnectionException( e.getMessage() );
        }
    }

    @Override
    public <V> V getAttribute(ConnectionAttribute name) {
        return (V) this.attributes.get(name);
    }

    @Override
    public void setAttribute(ConnectionAttribute name, Object value) {
        this.attributes.put(name, value);
    }

    @Override
    public boolean hasAttribute(ConnectionAttribute name) {
        return this.attributes.containsKey(name);
    }

    public SSHClient asRawConnection() {
        return this.connection;
    }
}
