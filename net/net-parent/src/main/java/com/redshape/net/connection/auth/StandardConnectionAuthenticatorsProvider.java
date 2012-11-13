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

        return this.authenticatorsRegistry.get(server.getType());
    }
}
