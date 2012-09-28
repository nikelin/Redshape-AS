package com.redshape.ascript.evaluation.functions.logical;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;

/**
 * @author nikelin
 * @date 20/04/11
 * @package com.redshape.ascript.evaluation.functions.conditional
 */
public class OrFunction extends Lambda<Boolean> {

	private IEvaluator evaluator;

	public OrFunction( IEvaluator evaluatorContext ) {
		this.evaluator = evaluatorContext;
	}

	@Override
	public Boolean invoke(Object... arguments) throws InvocationException {
		this.assertArgumentsType( arguments, Boolean.class );

		boolean result = true;
		for ( Object argument : arguments ) {
			result = result || (Boolean) argument;
		}

		return result;
	}

}
