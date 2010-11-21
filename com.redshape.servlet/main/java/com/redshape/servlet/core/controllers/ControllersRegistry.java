package com.redshape.servlet.core.controllers;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:23 AM
 * To change this template use File | Settings | File Templates.
 */
public final class ControllersRegistry {
    private static Set<Class<? extends IAction>> actions = new HashSet();

    public static void addAction( Class<? extends IAction> actionClazz ) {
        if ( actionClazz.getAnnotation( Action.class ) == null ) {
            throw new IllegalArgumentException("Invalid class given as action");
        }

        actions.add( actionClazz );
    }

    public static Set<Class<? extends IAction> > getActions() {
        return actions;
    }

    public static IAction createAction( String controller, String action ) throws InstantiationException {
        for ( Class<? extends IAction> actionClazz : getActions() ) {
            Action actionMeta = actionClazz.getAnnotation( Action.class );
            if ( actionMeta.controller().equals( controller )
                    && actionMeta.name().equals( action ) ) {
                return _createInstance( actionClazz );
            }
        }

        return null;
    }

    private static IAction _createInstance( Class<? extends IAction> action ) throws InstantiationException {
        try {
            return action.newInstance();
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }
    }

}
