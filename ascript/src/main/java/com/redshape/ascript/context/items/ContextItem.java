package com.redshape.ascript.context.items;

import com.redshape.ascript.EvaluationException;
import com.redshape.ascript.context.IEvaluationContext;
import com.redshape.ascript.context.IEvaluationContextItem;

import java.lang.reflect.Method;

public class ContextItem implements IEvaluationContextItem {
	private IEvaluationContext context;
	
	public ContextItem( IEvaluationContext context ) {
		this.context = context;
	}

	@Override
	public <V> V getValue(String name) throws EvaluationException {
		return (V) this.context.get(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getValue() throws EvaluationException {
		return (V) this.context;
	}

	@Override
	public Method getMethod(String name, int argumentsCount, Class<?>[] types ) throws EvaluationException {
		return this.context.resolveFunction( name, argumentsCount, types).toMethod();
	}
	
}
