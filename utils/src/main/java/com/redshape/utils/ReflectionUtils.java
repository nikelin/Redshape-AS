package com.redshape.utils;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Nov 8, 2010
 * Time: 1:18:07 PM
 * To change this template use File | Settings | File Templates.
 */
public final class ReflectionUtils {

    public static Class<?>[] getTypesList( Object... instances ) {
        Class<?>[] result = new Class[instances.length];
        for ( int i = 0; i < instances.length; i++ ) {
            result[i] = instances[i].getClass();
        }

        return result;
    }
}
