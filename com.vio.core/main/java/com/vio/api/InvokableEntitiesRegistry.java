package com.vio.api;

import com.vio.remoting.annotations.RemoteMethod;
import com.vio.remoting.annotations.RemoteService;
import com.vio.remoting.interfaces.RemoteInterface;
import com.vio.utils.InterfacesFilter;
import com.vio.utils.PackageLoaderException;
import com.vio.utils.Registry;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.rmi.Remote;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 11, 2010
 * Time: 1:04:21 PM
 * To change this template use File | Settings | File Templates.
 */
public final class InvokableEntitiesRegistry {
    private static final Logger log = Logger.getLogger( InvokableEntitiesRegistry.class);
    private static final Map<String, Class<?> > invokableEntities = new HashMap();
    private static final Map<String, Map<String, String>> invokableMethods = new HashMap();

    static {
        try {
            for ( Class<?> remoteService : Registry.getPackagesLoader().<Remote>getClasses( "com.vio.remoting.interfaces.impl", new InterfacesFilter( new Class[] { RemoteInterface.class }, new Class[] { RemoteService.class } ) ) ) {
                RemoteService service = remoteService.getAnnotation(RemoteService.class);
                if ( service == null ) {
                    continue;
                }

                invokableEntities.put( service.name(), remoteService );

                initializeEntityMethods( service.name() );
            }
        } catch ( PackageLoaderException e ) {
            log.error("Cannot load remote interfaces!", e );
        }
    }

    public static void registerEntity( String entityName, Class<?> entityClass ) {
        invokableEntities.put( entityName, entityClass );
        initializeEntityMethods( entityName );
    }


    public static boolean isRegistered( String entityName ) {
        return invokableEntities.containsKey(entityName);
    }

    public static String getRealMethodName( String entityName, String methodName ) {
        return invokableMethods.get(entityName).get(methodName);
    }

    public static boolean isInvokeAllowed( String entityName, String methodName ) {
        return isRegistered(entityName) && invokableMethods.containsKey(entityName)
                  && invokableMethods.get(entityName).get(methodName) != null;
    }

    public static Class<?> getEntity( String entityName ) {
        return invokableEntities.get(entityName);
    }

    private static void initializeEntityMethods( String entityName) {
        Class<?> remoteEntityClass = invokableEntities.get(entityName);

        Map<String, String> methods = new HashMap<String, String>();
        for ( Method method : remoteEntityClass.getMethods() ) {
            RemoteMethod annotation = method.getAnnotation(RemoteMethod.class);
            if ( annotation != null ) {
                continue;
            }

            methods.put( annotation.name(), method.getName() );
        }

        invokableMethods.put( entityName, methods );
    }

}
