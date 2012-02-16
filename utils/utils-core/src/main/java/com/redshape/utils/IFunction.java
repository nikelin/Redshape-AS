package com.redshape.utils;

import java.lang.reflect.InvocationTargetException;

public interface IFunction<V, T> {
	
	public void bind( V context );
	
	public T invoke() throws InvocationTargetException;
	
	public T invoke( Object... object ) throws InvocationTargetException;
	
	public T invoke( V context, Object... object ) throws InvocationTargetException;
	
	public IFunction<V, T> pass( Object... object );
	
}
