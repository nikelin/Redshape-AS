package com.redshape.utils;

import org.apache.log4j.Logger;

import java.lang.reflect.Method;

/**
 * 
 * @author nikelin
 *
 * @param <V>
 * @param <T>
 */
public class Function<V, T> extends Lambda<T> implements IFunction<V, T> {
	private static final Logger log = Logger.getLogger(Function.class);

	private Method method;
	private V bind;
    
    public Function( V bind, Method method ) {
        this( bind, method, new Object[] {} );
    }
    
    public Function( V bind, Method method, Object... arguments ) {
		super(arguments);

        this.bind = bind;
		this.method = method;
	}
	
	protected Method getMethod() {
		return this.method;
	}

	protected void assertTrue( Boolean condition, String message ) {
		if ( !condition ) {
			throw new IllegalArgumentException(message);
		}
	}

	protected void assertArgumentType( Object argument, Class<?> type ) {
		this.assertArgumentsType( new Object[] { argument }, type );
	}

	protected void assertArgumentsType( Object[] arguments, Class<?> type ) {
		for ( Object argument : arguments ) {
			if ( !type.isAssignableFrom( argument.getClass() ) ) {
				throw new IllegalArgumentException("Wrong argument type " + argument.getClass().getCanonicalName()
						+ " when expected " + type.getCanonicalName()  );
			}
		}
	}

	protected void assertArgumentsCount( Object[] actual, int count ) {
		if ( actual.length != count ) {
			throw new IllegalArgumentException("Wrong arguments count " + actual.length + " when expected " + count );
		}
	}
	
	@Override
	public void bind( V context ) {
		this.bind = context;
	}
	
	protected V getBind() {
		return this.bind;
	}
	
	@Override
	public T invoke() throws InvocationException {
		return this.invoke( new Object[] {} );
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public T invoke( Object... arguments) throws InvocationException {
		T result = null;
		
		int argumentsCount = this.getArguments().length + arguments.length;
		if ( argumentsCount != this.getMethod().getParameterTypes().length ) {
			throw new IllegalArgumentException("Invalid arguments count. Given: " + argumentsCount 
						+ "; Actual: " + this.getMethod().getParameterTypes().length );
		}
		
		Object[] args = arguments;
		if ( this.getArguments().length > 0 ) {
			args = new Object[ this.getArguments().length + arguments.length];
			int i = 0;
			
			for ( Object arg : arguments ) {
				args[i++] = arg;
			}
			
			for ( Object arg : this.getArguments() ) {
				args[i++] = arg;
			}
		}
		
		try {
			result = (T) this.getMethod().invoke( this.getBind(), args);
		} catch ( Throwable e  ) {
            throw new InvocationException( e.getMessage(), e );
		}
		
		return result;
	}

	@Override
	public com.redshape.utils.IFunction<V, T> pass(Object... arguments) {
		return new Function<V, T>( this.getBind(), this.getMethod(), arguments);
	}


    @Override
    public Method toMethod() {
        return this.method;
    }
}