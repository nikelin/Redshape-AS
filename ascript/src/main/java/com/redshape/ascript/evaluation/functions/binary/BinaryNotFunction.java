package com.redshape.ascript.evaluation.functions.binary;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.Function;

/**
 * @author nikelin
 * @date 20/04/11
 * @package com.redshape.ascript.evaluation.functions.binary
 */
public class BinaryNotFunction extends Function<Object, Integer> {

	private IEvaluator evaluator;

	public BinaryNotFunction( IEvaluator evaluatorContext ) {
		this.evaluator = evaluatorContext;
	}

	public Integer invoke( Object... args ) {
		this.assertArgumentsCount( args, 1 );
		this.assertArgumentType( args[0], Integer.class );

		return ~ ( (Integer) args[1] );
	}

}
