package com.redshape.ascript.evaluation.functions.arrays;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;

import java.util.List;

/**
 * @author nikelin
 * @date 20/04/11
 * @package com.redshape.ascript.evaluation.functions.arrays
 */
public class LengthFunction extends Lambda<Integer> {

	private IEvaluator evaluator;

	public LengthFunction( IEvaluator evaluatorContext ) {
		this.evaluator = evaluatorContext;
	}

	@Override
	public Integer invoke(Object... arguments) throws InvocationException {
		this.assertArgumentsCount( arguments, 1 );
		this.assertArgumentType( arguments[0], List.class );

		return ( (List) arguments[0] ).size();
	}
}
