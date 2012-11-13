/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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


