/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.net.ssh.connection;

import com.redshape.net.IServer;
import com.redshape.net.connection.ConnectionAttribute;
import com.redshape.net.connection.ConnectionException;
import com.redshape.net.connection.IServerConnection;
import com.redshape.net.connection.auth.IConnectionAuthenticator;
import com.redshape.utils.Commons;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;
import net.schmizz.sshj.SSHClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nikelin
 * @date 13:25
 */
public class SshConnectionSupport implements IServerConnection {

    @Autowired( required = true )
    private IConfig config;

    private IServer server;
    private SSHClient connection;
    private IConnectionAuthenticator authenticator;
    private Map<ConnectionAttribute, Object> attributes = new HashMap<ConnectionAttribute, Object>();

    public SshConnectionSupport( IServer server, IConnectionAuthenticator authenticator ) {
        Commons.checkNotNull(server);
        Commons.checkNotNull(authenticator);

        this.server = server;
        this.authenticator = authenticator;
    }

    public IConfig getConfig() {
        return config;
    }

    public void setConfig(IConfig config) {
        this.config = config;
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
            this.loadKeys();
            this.connection.setConnectTimeout(
                Commons.select(this.<Integer>getAttribute(ConnectionAttribute.TIMEOUT), 30000)
            );

            URI uri = server.toURI();
            this.connection.connect(uri.getHost(), uri.getPort());

            this.getAuthenticator().authenticate( this.getServer(), this.getConnection() );
        } catch ( Throwable e ) {
            throw new ConnectionException( e.getMessage(), e );
        }
    }

    protected void loadKeys() throws ConfigException {
        for ( IConfig value : this.getConfig().get("ssh.verified").childs() ) {
            this.connection.addHostKeyVerifier(value.value());
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
