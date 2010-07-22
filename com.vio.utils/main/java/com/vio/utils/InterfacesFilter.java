package com.vio.utils;

import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 22, 2010
 * Time: 1:48:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class InterfacesFilter implements Filter<Class<?>> {
    private static final Logger log = Logger.getLogger( InterfacesFilter.class );
    
    private Class<?>[] interfaces;
    private Class<? extends Annotation>[] annotations;
    private boolean allowNesting;

    public InterfacesFilter( Class<?>[] interfaces ) {
        this( interfaces, new Class[] {} );
    }

    public InterfacesFilter( Class<?>[] interfaces, boolean allowNesting ) {
        this( interfaces, new Class[] {}, allowNesting );
    }

    public InterfacesFilter( Class<?>[] interfaces, Class<? extends Annotation>[] annotations ) {
        this( interfaces, annotations, true );
    }

    public InterfacesFilter( Class<?>[] interfaces, Class<? extends Annotation>[] annotations, boolean allowNesting ) {
        this.interfaces = interfaces;
        this.annotations = annotations;
        this.allowNesting = allowNesting;
    }

    public boolean filter( Class<?> filterable ) {
        return this.filter( filterable, this.allowNesting );
    }

    public boolean filter( Class<?> filterable, boolean allowNesting ) {
        for ( Class<? extends Annotation> annon : this.annotations ) {
            Class<?> parent = filterable;
            boolean found;
            do {
                found = parent.isAnnotationPresent(annon);
            } while( allowNesting && !found && null != ( parent = parent.getSuperclass() ) );

            if ( !found ) {
                return false;
            }
        }

        for ( Class<?> interfaceClass : this.interfaces ) {
            if ( !interfaceClass.isAssignableFrom( filterable ) ) {
                return false;
            }
        }

        return true;
    }

}
