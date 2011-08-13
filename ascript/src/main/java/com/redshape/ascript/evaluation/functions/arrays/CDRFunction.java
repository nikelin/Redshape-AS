package com.redshape.ascript.evaluation.functions.arrays;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.Function;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author nikelin
 * @date 20/04/11
 * @package com.api.deployer.expressions.evaluation.functions.arrays
 */
public class CDRFunction extends Function<Object, List<?>> {

	private IEvaluator evaluator;

	public CDRFunction( IEvaluator evaluatorContext ) {
		this.evaluator = evaluatorContext;
	}

	@Override
	public List<?> invoke( Object... args ) throws InvocationTargetException {
		try {
			this.assertArgumentsCount( args, 1 );
			this.assertArgumentType( args[0], List.class );

			return ( (List) args[0] ).subList( 1, ( (List) args[0]).size() );
		} catch ( Throwable e ) {
			throw new InvocationTargetException( e );
		}
	}
}
