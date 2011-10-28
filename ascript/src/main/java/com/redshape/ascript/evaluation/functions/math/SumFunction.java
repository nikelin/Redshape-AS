package com.redshape.ascript.evaluation.functions.math;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.Function;

import java.lang.reflect.InvocationTargetException;

/**
 * @author nikelin
 * @date 20/04/11
 * @package com.redshape.ascript.evaluation.functions.math
 */
public class SumFunction extends Function<Object, Double> {

	private IEvaluator evaluator;

	public SumFunction( IEvaluator evaluatorContext ) {
		this.evaluator = evaluatorContext;
	}

	@Override
	public Double invoke(Object... arguments) throws InvocationTargetException {
		this.assertArgumentsType( arguments, Number.class );

		Double result = 0D;
		for ( Object arg : arguments ) {
			result += (Double) arg;
		}

		return result;
	}

}
