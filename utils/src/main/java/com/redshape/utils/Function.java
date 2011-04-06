package com.redshape.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * @author nikelin
 *
 * @param <V>
 * @param <T>
 */
public class Function<V, T> implements IFunction<V, T> {
	private Method method;
	private V bind;
	private Object[] arguments;
	
	public Function() {
		this(null);
	}
	
	public Function ( String name, Class<V> context, 
					  Class<?>... arguments ) throws NoSuchMethodException{
		this( context.getMethod(name, arguments) );
	}
	
	public Function( Method method ) {
		this(method, new Object[] {} );
	}
	
	public Function( Method method, Object... arguments ) {		
		this.arguments = arguments;
		this.method = method;
	}
	
	protected Method getMethod() {
		return this.method;
	}
	
	@Override
	public void bind( V context ) {
		this.bind = context;
	}
	
	protected V getBind() {
		return this.bind;
	}
	
	@Override
	public T invoke() throws InvocationTargetException {
		return this.invoke( new Object[] {} );
	}
	
	@Override
	public T invoke( Object... arguments ) throws InvocationTargetException {
		return this.invoke( this.getBind(), arguments );
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T invoke( V context, Object... arguments) throws InvocationTargetException {
		T result = null;
		
		int argumentsCount = this.arguments.length + arguments.length;
		if ( argumentsCount != this.getMethod().getParameterTypes().length ) {
			throw new IllegalArgumentException("Invalid arguments count. Given: " + argumentsCount 
						+ "; Actual: " + this.getMethod().getParameterTypes().length );
		}
		
		Object[] args = arguments;
		if ( this.arguments.length > 0 ) {
			args = new Object[ this.arguments.length + arguments.length];
			int i = 0;
			
			for ( Object arg : arguments ) {
				args[i++] = arg;
			}
			
			for ( Object arg : this.arguments ) {
				args[i++] = arg;
			}
		}
		
		try {
			result = (T) this.getMethod().invoke( context, args);
		} catch ( Throwable e  ) {
			throw new InvocationTargetException( e ); 
		}
		
		return result;
	}

	@Override
	public IFunction<V, T> pass(Object... arguments) {
		return new Function<V, T>( this.getMethod(), arguments);
	}
	
	@Override
	public Method toMethod() {
		return this.method;
	}

}
