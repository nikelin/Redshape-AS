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
    public static <T extends IVersionsRegistry> T getInstance( Class<T> clazz ) throws InstantiationException {
        return InstancesHandler.getInstance( clazz );
    }

    public static <T extends IVersionsRegistry> T newInstance( Class<T> clazz ) throws InstantiationException {
        return InstancesHandler.newInstance( clazz );
    }

    final static class InstancesHandler {
        private static final Map<Class<? extends IVersionsRegistry>, IVersionsRegistry> instances = new HashMap();

        @SuppressWarnings("unchecked")
		public static <T extends IVersionsRegistry> T getInstance( Class<T> registryClass )
            throws InstantiationException {
            T registryInstance = (T) instances.get(registryClass);
            if ( registryInstance == null ) {
                instances.put( (Class<T>) registryClass, (T) ( registryInstance = newInstance( registryClass ) ) );
            }

            return registryInstance;
        }

        synchronized public static <T extends IVersionsRegistry> T newInstance( Class<T> registryClazz )
            throws InstantiationException {
            T instance;

            try {
                instance = registryClazz.newInstance();
            } catch ( Throwable e ) {
                throw new InstantiationException();
            }

            return instance;
        }

    }

}
