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
