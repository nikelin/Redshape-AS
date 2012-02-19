package com.redshape.ascript.evaluation.functions.arrays;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.Lambda;

import java.util.List;

/**
 * @author nikelin
 * @date 20/04/11
 * @package com.redshape.ascript.evaluation.functions.arrays
 */
public class EmptyFunction extends Lambda<Boolean> {

	private IEvaluator evaluator;

	public EmptyFunction( IEvaluator evaluatorContext ) {
		this.evaluator = evaluatorContext;
	}

	@Override
	public Boolean invoke( Object... args ) {
		this.assertArgumentsCount(args, 1);
		this.assertArgumentType(args[0], List.class);

		return ( (List) args[0] ).isEmpty();
	}

}
