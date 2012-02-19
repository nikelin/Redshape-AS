package com.redshape.ascript.context.items;

import com.redshape.ascript.EvaluationException;
import com.redshape.ascript.context.IEvaluationContextItem;
import com.redshape.utils.IFunction;
import com.redshape.utils.ILambda;

import java.lang.reflect.Method;

public class FunctionItem implements IEvaluationContextItem {
	private ILambda<?> function;
	
	public FunctionItem( ILambda<?> function ) {
		this.function = function;
	}

	@Override
	public Method getMethod(String name, int argumentsCount, Class<?>[] types )
			throws EvaluationException {
        if ( this.function instanceof IFunction ) {
		    return ( (IFunction) this.function).toMethod();
        } else {
            throw new EvaluationException("Unable to convert lambda(x) to class method");
        }
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
