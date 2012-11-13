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

package com.redshape.utils.beans.bindings;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class BeanConstructor<T> {
	private Member member;
	
	public BeanConstructor( Method method ) {
		this.member = method;
	}
	
	public BeanConstructor( Constructor<T> constructor ) {
		this.member = constructor;
	}
	
	@SuppressWarnings("unchecked")
	public T newInstance( Object...objects ) throws IllegalArgumentException,
													InstantiationException, 
													IllegalAccessException, 
													InvocationTargetException {
		if ( this.member instanceof Constructor ) {
			return ( (Constructor<T>) this.member ).newInstance(objects);
		} else {
			return (T) ( (Method) this.member ).invoke(null, objects);
		}
	}
	
}
