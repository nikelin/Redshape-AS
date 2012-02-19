package com.redshape.ascript.evaluation.functions;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;

/**
 * @author nikelin
 * @date 20/04/11
 * @package com.redshape.ascript.expressions.evaluation.functions
 */
public class BeginFunction extends Lambda<Object> {

	private IEvaluator evaluator;

	public BeginFunction( IEvaluator evaluatorContext ) {
		this.evaluator = evaluatorContext;
	}

	@Override
	public Object invoke(Object... arguments) throws InvocationException {
		return arguments.length != 0 ? arguments[arguments.length-1] : null;
	}

}