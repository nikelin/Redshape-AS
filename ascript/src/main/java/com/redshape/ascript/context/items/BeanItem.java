package com.redshape.ascript.context.items;

import com.redshape.ascript.EvaluationException;
import com.redshape.ascript.context.IEvaluationContextItem;
import com.redshape.utils.ILambda;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;
import com.redshape.utils.ReflectionUtils;
import com.redshape.utils.beans.PropertyUtils;

import java.lang.reflect.Method;

public class BeanItem implements IEvaluationContextItem {
	private Class<?> clazz;
	private Object instance;
	
	public BeanItem( Class<?> clazz, Object instance ) {
		this.clazz = clazz;
		this.instance = instance;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <V> V getValue(String name) throws EvaluationException {
		try {
			if ( name != null ) {
				return (V) PropertyUtils.getInstance().getProperty( this.clazz, name).get(this.instance);
			} else {
				return (V) this;
			}
		} catch ( Throwable e ) {
			throw new EvaluationException("Requested object or field is inaccessible");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getValue() {
		return (V) this.instance;
	}

	@Override
	public <T> ILambda<T> getMethod(String name, int argumentsCount, Class<?>[] types ) throws EvaluationException {
		try {
			for ( final Method m : this.clazz.getMethods() ) {
				if ( m.getName().equals( name ) && m.getParameterTypes().length == argumentsCount
						&& ReflectionUtils.compareTypeLists(m.getParameterTypes(), types) ) {
					return new Lambda<T>() {
                        @Override
                        public T invoke(Object... object) throws InvocationException {
                            try {
                                return (T) m.invoke( BeanItem.this.instance );
                            } catch ( Throwable e ) {
                                throw new InvocationException( e.getMessage(), e );
                            }
                        }
                    };
				}
			}
			
			throw new EvaluationException("Requested method ( " + name + " " + argumentsCount + "-arged ) not found");
		} catch ( Throwable e ) {
			throw new EvaluationException( e.getMessage(), e );
		}
	}

}
