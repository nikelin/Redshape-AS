package com.vio.server;

import java.util.Map;

/**
 * Factory class which responsible for producing
 * configured IServer implementations instances.
 *
 * @author nikelin
 * @group IO
 */
public class ServerFactory extends AbstractServerFactory {

    @Override
    public <T extends IServer> T newInstance( Class<T> serverClass, String host, Integer port )
        throws InstantiationException {
        return this.newInstance( serverClass, host, port, false );
    }

    @Override
    public <T extends IServer> T newInstance( Class<T> serverClass, String host, Integer port,
                                              Boolean isSSLEnabled )
        throws InstantiationException {
        return this.newInstance( serverClass, host, port, isSSLEnabled, this.getProperties() );
    }

    @Override
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
