package com.redshape.ascript.evaluation.functions.binary;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;

/**
 * @author nikelin
 * @date 20/04/11
 * @package com.redshape.ascript.evaluation.functions.binary
 */
public class LeftShiftFunction extends Lambda<Integer> {

	private IEvaluator evaluator;

	public LeftShiftFunction( IEvaluator evaluatorContext ) {
		this.evaluator = evaluatorContext;
	}

	@Override
	public Integer invoke(Object... arguments) throws InvocationException {
		this.assertArgumentsCount( arguments, 2 );
		this.assertArgumentType( arguments[0], Integer.class );
		this.assertArgumentType( arguments[1], Integer.class );

		return (Integer) arguments[0] << (Integer) arguments[1];
	}
}
