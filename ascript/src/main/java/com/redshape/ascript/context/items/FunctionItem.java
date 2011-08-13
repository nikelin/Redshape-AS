package com.redshape.ascript.context.items;

import com.redshape.ascript.EvaluationException;
import com.redshape.ascript.context.IEvaluationContextItem;
import com.redshape.utils.IFunction;

import java.lang.reflect.Method;

public class FunctionItem implements IEvaluationContextItem {
	private IFunction<?,?> function;
	
	public FunctionItem( IFunction<?,?> function ) {
		this.function = function;
	}

	@Override
	public Method getMethod(String name, int argumentsCount, Class<?>[] types )
			throws EvaluationException {
		return this.function.toMethod();
	}

	@Override
	public <V> V getValue(String name) throws EvaluationException {
		throw new EvaluationException("Method not supported on function item");
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getValue() throws EvaluationException {
		return (V) this.function;
	}
	
}
