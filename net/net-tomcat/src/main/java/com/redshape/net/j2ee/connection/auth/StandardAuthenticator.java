package com.redshape.net.j2ee.connection.auth;

import com.redshape.net.IServer;
import com.redshape.net.connection.auth.AuthenticationException;
import com.redshape.net.connection.auth.credentials.IServerCredentials;
import com.redshape.net.connection.auth.credentials.IServerCredentialsProvider;
import com.redshape.net.j2ee.connection.auth.credentials.AuthPairCredentials;
import com.redshape.net.j2ee.connection.auth.credentials.PropertiesCredentials;
import com.redshape.net.jmx.JMXAgent;
import com.redshape.utils.Commons;
import com.redshape.utils.IResourcesLoader;
import com.redshape.utils.config.IConfig;

/**
 * @author nikelin
 * @date 21:28
 */
public class StandardAuthenticator implements ITomcatAuthenticator {
    private IConfig config;
    private IServerCredentialsProvider provider;
    private IResourcesLoader resourcesLoader;

    public StandardAuthenticator(IServerCredentialsProvider provider,
                                 IConfig config,
                                 IResourcesLoader resourcesLoader) {
        Commons.checkNotNull(provider);
        Commons.checkNotNull(config);
        Commons.checkNotNull(resourcesLoader);

        this.provider = provider;
        this.config = config;
        this.resourcesLoader = resourcesLoader;
    }

    protected IConfig getConfig() {
        return config;
    }

    protected IServerCredentialsProvider getProvider() {
        return provider;
    }

    protected IResourcesLoader getResourcesLoader() {
        return resourcesLoader;
    }

    @Override
    public void authenticate(IServer server, JMXAgent connection) throws AuthenticationException {
        boolean authenticated = false;
        for (IServerCredentials credentials : this.getProvider().provide(server) ) {
            if ( (credentials instanceof PropertiesCredentials) ) {
                authenticated = this.authProperties(connection, (PropertiesCredentials) credentials);
            } else if ( (credentials instanceof AuthPairCredentials ) ) {
                authenticated = this.authPair(connection, (AuthPairCredentials) credentials );
            } else {
                authenticated = this.authGeneric(connection, credentials);
            }

            if ( authenticated ) {
                break;
            }
        }

        if ( !authenticated ) {
            throw new AuthenticationException("Authentication failed");
        }
    }

    protected boolean authGeneric( JMXAgent connection, IServerCredentials credentials ) {
        return false;
    }

    protected boolean authProperties( JMXAgent connection, PropertiesCredentials credentials ) {
        connection.setRemotePasswordProperties( credentials.getPasswordFile() );
        connection.setRemoteAccessProperties( credentials.getAccessFile() );

        return true;
    }

    protected boolean authPair( JMXAgent connection, AuthPairCredentials credentials ) {
        connection.setRemoteUser( credentials.getUsername() );
        connection.setRemotePassword( credentials.getPassword() );

        return true;
    }
}
