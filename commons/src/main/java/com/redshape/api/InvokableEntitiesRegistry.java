package com.redshape.api;

import com.redshape.io.remoting.annotations.RemoteMethod;
import com.redshape.io.remoting.interfaces.RemoteInterface;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;

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
    private static final Map<String, 
    						RemoteInterface> invokableEntities = new HashMap<String, RemoteInterface>();
    private static final Map<String, 
    						Map<String, String>> invokableMethods = new HashMap<String,
    																			Map<String, String>>();

    public static void registerEntity( String entityName, RemoteInterface entityClass ) {
        invokableEntities.put( entityName, entityClass );
        initializeEntityMethods( entityName );
    }

    public static Map<String, RemoteInterface> getRegistered() {
        return invokableEntities;
    }

    public static boolean isRegistered( String entityName ) {
        return invokableEntities.containsKey(entityName);
    }

    public static String getRealMethodName( String entityName, String methodName ) {
        return invokableMethods.get(entityName).get(methodName);
    }

    public static boolean isInvokeAllowed( String entityName, String methodName ) {
        log.info( invokableMethods );
        return isRegistered(entityName) && invokableMethods.containsKey(entityName)
                  && invokableMethods.get(entityName).get(methodName) != null;
    }

    public static RemoteInterface getEntity( String entityName ) {
        return invokableEntities.get(entityName);
    }

    private static void initializeEntityMethods( String entityName) {
        log.info("Service " + entityName + " initilization...");
        log.info( "Methods count: " + invokableEntities.get(entityName).getClass().getMethods().length );

        Map<String, String> methods = new HashMap<String, String>();
        for ( Method method : invokableEntities.get(entityName).getClass().getMethods() ) {
            log.info("Method: " + method.getName() );
            RemoteMethod annotation = method.getAnnotation(RemoteMethod.class);
            if ( annotation == null ) {
                continue;
            }

            log.info( annotation.name() + ":" + method.getName() );
            methods.put( annotation.name(), method.getName() );
        }

        invokableMethods.put( entityName, methods );
    }

}
