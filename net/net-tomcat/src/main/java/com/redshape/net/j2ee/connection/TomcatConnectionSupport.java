package com.redshape.net.j2ee.connection;

import com.redshape.net.IServer;
import com.redshape.net.connection.ConnectionAttribute;
import com.redshape.net.connection.ConnectionException;
import com.redshape.net.connection.IServerConnection;
import com.redshape.net.connection.auth.AuthenticationException;
import com.redshape.net.j2ee.connection.auth.ITomcatAuthenticator;
import com.redshape.net.jmx.JMXAgent;
import com.redshape.net.jmx.JMXException;
import com.redshape.net.jmx.JMXFactory;
import com.redshape.utils.Commons;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author nikelin
 * @date 19:15
 */
public class TomcatConnectionSupport implements IServerConnection {

    @Autowired( required = true )
    private JMXFactory factory;

    private JMXAgent agent;
    private IServer server;
    private ITomcatAuthenticator authenticator;

    public TomcatConnectionSupport( IServer server, ITomcatAuthenticator authenticator ) {
        Commons.checkNotNull(server);
        Commons.checkNotNull(authenticator);

        this.server = server;
        this.authenticator = authenticator;
    }

    protected IServer getServer() {
        return server;
    }

    protected ITomcatAuthenticator getAuthenticator() {
        return authenticator;
    }

    protected JMXFactory getFactory() {
        return factory;
    }

    public void setFactory(JMXFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean isConnected() {
        return this.agent != null;
    }

    @Override
    public void connect() throws ConnectionException {
        try {
            this.agent = this.getFactory().createAgent();
            this.getAuthenticator().authenticate( this.getServer(), this.agent );
            this.agent.start();
        } catch ( JMXException e ) {
            throw new ConnectionException( e.getMessage(), e );
        } catch ( AuthenticationException e ) {
            throw new ConnectionException( e.getMessage(), e );
        }
    }

    @Override
    public void disconnect() throws ConnectionException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <V> V getAttribute(ConnectionAttribute name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setAttribute(ConnectionAttribute name, Object value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean hasAttribute(ConnectionAttribute name) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public JMXAgent asRawConnection() {
            return this.agent;
        }
}
