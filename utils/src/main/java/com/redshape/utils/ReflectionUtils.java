package com.redshape.utils;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Nov 8, 2010
 * Time: 1:18:07 PM
 * To change this template use File | Settings | File Templates.
 */
public final class ReflectionUtils {

	public static boolean compareTypeLists( Class<?>[] first, Class<?>[] second ) {
		return compareTypeLists( first, second, false );
	}

	public static boolean compareTypeLists( Class<?>[] first, Class<?>[] second, boolean strict ) {
		for ( int i = 0; i < first.length; i++ ) {
			if ( strict ) {
				if ( !first[i].equals(second[i]) ) {
					return false;
				}
			} else {
				if ( !first[i].isAssignableFrom( second[i] ) ) {
					return false;
				}
			}
		}

		return true;
	}

    public static Class<?>[] getTypesList( Object... instances ) {
        Class<?>[] result = new Class[instances.length];
        for ( int i = 0; i < instances.length; i++ ) {
            result[i] = instances[i].getClass();
        }

        return result;
    }
}
