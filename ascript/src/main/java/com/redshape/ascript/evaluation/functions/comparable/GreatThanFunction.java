package com.redshape.ascript.evaluation.functions.comparable;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;

/**
 * @author nikelin
 * @date 20/04/11
 * @package com.redshape.ascript.evaluation.functions.conditional
 */
public class GreatThanFunction extends Lambda<Boolean> {

	private IEvaluator evaluator;

	public GreatThanFunction( IEvaluator evaluatorContext ) {
		this.evaluator = evaluatorContext;
	}

	@Override
	public Boolean invoke(Object... arguments) throws InvocationException {
		this.assertArgumentsType( arguments, Comparable.class );
		this.assertArgumentsCount( arguments, 2 );

		return 1 == ( ( (Comparable) arguments[0] ).compareTo( (Comparable) arguments[1] ) );
	}

}