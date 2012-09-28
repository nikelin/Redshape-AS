package com.redshape.ascript.evaluation.functions.comparable;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;

/**
 * @author nikelin
 * @date 20/04/11
 * @package com.redshape.ascript.evaluation.functions.comparable
 */
public class EqualsFunction extends Lambda<Boolean> {

	private IEvaluator evaluator;

	public EqualsFunction( IEvaluator evaluatorContext ) {
		this.evaluator = evaluatorContext;
	}

	@Override
	public Boolean invoke(Object... arguments) throws InvocationException {
		this.assertArgumentsCount( arguments, 2 );

		return arguments[0].equals( arguments[1] );
	}
}
