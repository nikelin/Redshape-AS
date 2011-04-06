package com.redshape.utils.helpers;

import java.lang.reflect.Method;
import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 5, 2010
 * Time: 12:15:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class MethodsComparator implements Comparator<Method> {

    public int compare( Method m1, Method m2 ) {
        return m1.getName().compareTo( m2.getName() );
    }

    public boolean equals( Object o ) {
        return this.getClass().equals( o.getClass() );
    }

}
