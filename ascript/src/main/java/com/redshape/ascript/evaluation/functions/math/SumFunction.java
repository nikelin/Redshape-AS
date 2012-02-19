package com.redshape.ascript.evaluation.functions.math;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;

/**
 * @author nikelin
 * @date 20/04/11
 * @package com.redshape.ascript.evaluation.functions.math
 */
public class SumFunction extends Lambda<Double> {

	private IEvaluator evaluator;

	public SumFunction( IEvaluator evaluatorContext ) {
		this.evaluator = evaluatorContext;
	}

	@Override
	public Double invoke(Object... arguments) throws InvocationException {
		this.assertArgumentsType( arguments, Number.class );

		Double result = 0D;
		for ( Object arg : arguments ) {
			result += (Double) arg;
		}

		return result;
	}

}
