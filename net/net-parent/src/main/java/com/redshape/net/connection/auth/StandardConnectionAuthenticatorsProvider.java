package com.redshape.net.connection.auth;

import com.redshape.net.IServer;
import com.redshape.net.ServerType;
import com.redshape.utils.Commons;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 3/21/12
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class StandardConnectionAuthenticatorsProvider implements IConnectionAuthenticatorsProvider {
    private Map<ServerType, IConnectionAuthenticator> authenticatorsRegistry =
            new HashMap<ServerType, IConnectionAuthenticator>();
    private Map<IServer, IConnectionAuthenticator> explicitAuthenticatorsRegistry =
            new HashMap<IServer, IConnectionAuthenticator>();

    public StandardConnectionAuthenticatorsProvider(Map<ServerType, IConnectionAuthenticator> authenticatorsRegistry) {
        this(authenticatorsRegistry, new HashMap<IServer, IConnectionAuthenticator>() );
    }

    public StandardConnectionAuthenticatorsProvider(Map<ServerType, IConnectionAuthenticator> authenticatorsRegistry,
                                        Map<IServer, IConnectionAuthenticator> explicitAuthenticatorsRegistry) {
        Commons.checkNotNull(authenticatorsRegistry);
        Commons.checkNotNull(explicitAuthenticatorsRegistry);

        this.authenticatorsRegistry = authenticatorsRegistry;
        this.explicitAuthenticatorsRegistry = explicitAuthenticatorsRegistry;
    }

    public void addExplicitAuthenticator( IServer server, IConnectionAuthenticator<?> authenticator ) {
        Commons.checkNotNull(server);
        Commons.checkNotNull(authenticator);

        this.explicitAuthenticatorsRegistry.put( server, authenticator );
    }

    public void addAuthenticator( ServerType type, IConnectionAuthenticator<?> authenticator ) {
        Commons.checkNotNull(type);
        Commons.checkNotNull(authenticator);

        this.authenticatorsRegistry.put(type, authenticator);
    }

    @Override
    public <T> IConnectionAuthenticator<T> provide(IServer server) {
        Commons.checkNotNull(server);

        IConnectionAuthenticator<T> authenticator = this.explicitAuthenticatorsRegistry.get(server);
        if ( authenticator != null ) {
            return authenticator;
        }

        return this.explicitAuthenticatorsRegistry.get(server.getType());
    }
}
