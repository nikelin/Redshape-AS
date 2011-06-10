package com.redshape.utils;

import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;

/**
* Created by IntelliJ IDEA.
* User: nikelin
* Date: Feb 22, 2010
* Time: 1:48:24 PM
* To change this template use File | Settings | File Templates.
*/
public class InterfacesFilter<T> implements IFilter<Class<T>> {
    @SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger( InterfacesFilter.class );

    private Class<T>[] interfaces;
    private Class<? extends Annotation>[] annotations;
    private boolean allowNesting;

	@SuppressWarnings("unchecked")
	public InterfacesFilter( Class<T>[] interfaces ) {
        this( interfaces, new Class[] {} );
    }

    @SuppressWarnings("unchecked")
	public InterfacesFilter( Class<T>[] interfaces, boolean allowNesting ) {
        this( interfaces, new Class[] {}, allowNesting );
    }

    public InterfacesFilter( Class<T>[] interfaces, Class<? extends Annotation>[] annotations ) {
        this( interfaces, annotations, true );
    }

    public InterfacesFilter( Class<T>[] interfaces, Class<? extends Annotation>[] annotations, boolean allowNesting ) {
        this.interfaces = interfaces;
        this.annotations = annotations;
        this.allowNesting = allowNesting;
    }

    @Override
    public boolean filter( Class<T> filterable ) {
        return this.filter( filterable, this.allowNesting );
    }

    public boolean filter( Class<T> filterable, boolean allowNesting ) {
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

        for ( Class<T> interfaceClass : this.interfaces ) {
            if ( !interfaceClass.isAssignableFrom( filterable ) ) {
                return false;
            }
        }

        return true;
    }

}


