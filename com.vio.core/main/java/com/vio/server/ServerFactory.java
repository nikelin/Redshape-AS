package com.vio.server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 2:33:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerFactory extends AbstractServerFactory {

    public <T extends IServer> T newInstance( Class<T> serverClass, String host, Integer port )
        throws InstantiationException {
        return this.newInstance( serverClass, host, port, false );
    }

    public <T extends IServer> T newInstance( Class<T> serverClass, String host, Integer port,
                                              Boolean isSSLEnabled )
        throws InstantiationException {
        return this.newInstance( serverClass, host, port, isSSLEnabled, this.getProperties() );
    }

    public <T extends IServer> T newInstance( Class<T> serverClass, String host,
                              Integer port, Boolean isSSLEnabled, Map<String, Object> properties )
        throws InstantiationException {
        try {
            T instance = serverClass.newInstance();
            instance.setHost(host);
            instance.setPort(port);
            instance.enableSSL(isSSLEnabled);

            this.bindPolicies( instance, this.getPolicies() );
            this.bindProperties( instance, properties );

            return instance;
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }
    }


    public static IServerFactory getDefault() {
        return DefaultInstanceHandler.defaultInstance;
    }

    public static void setDefault( IServerFactory factory ) {
        DefaultInstanceHandler.defaultInstance = factory;
    }

    final static class DefaultInstanceHandler {
        volatile private static IServerFactory defaultInstance = createDefault();

        public static IServerFactory createDefault() {
            return new ServerFactory();
        }
    }
    
}
