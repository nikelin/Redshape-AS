package com.redshape.io.protocols.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 10:56:24 AM
 * To change this template use File | Settings | File Templates.
 */
public final class VersionRegistryFactory {
    public static IVersionsRegistry getInstance( Class<? extends IVersionsRegistry> clazz ) throws InstantiationException {
        return InstancesHandler.getInstance( clazz );
    }

    public static IVersionsRegistry newInstance( Class<? extends IVersionsRegistry> clazz ) throws InstantiationException {
        return InstancesHandler.newInstance( clazz );
    }

    final static class InstancesHandler {
        private static final Map<Class<? extends IVersionsRegistry>, IVersionsRegistry> instances = new HashMap();

        public static IVersionsRegistry getInstance( Class<? extends IVersionsRegistry> registryClass )
            throws InstantiationException {
            IVersionsRegistry registryInstance = instances.get(registryClass);
            if ( registryInstance == null ) {
                instances.put( registryClass, registryInstance = newInstance( registryClass ) );
            }

            return registryInstance;
        }

        synchronized public static IVersionsRegistry newInstance( Class<? extends IVersionsRegistry> registryClazz )
            throws InstantiationException {
            IVersionsRegistry instance;

            try {
                instance = registryClazz.newInstance();
            } catch ( Throwable e ) {
                throw new InstantiationException();
            }

            return instance;
        }

    }

}
