package com.redshape.ascript.evaluation.functions.logical;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;

/**
 * @author nikelin
 * @date 20/04/11
 * @package com.redshape.ascript.evaluation.functions.conditional
 */
public class NotFunction extends Lambda<Boolean> {

	private IEvaluator evaluator;

	public NotFunction( IEvaluator evaluatorContext ) {
		this.evaluator = evaluatorContext;
	}

	@Override
	public Boolean invoke(Object... arguments) throws InvocationException {
		this.assertArgumentsCount( arguments, 1 );
		this.assertArgumentType( arguments[0], Boolean.class );

		return ! ( (Boolean) arguments[0] );
	}

}
