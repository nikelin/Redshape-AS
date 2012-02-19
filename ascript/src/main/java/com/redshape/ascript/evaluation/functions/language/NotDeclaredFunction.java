package com.redshape.ascript.evaluation.functions.language;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;

/**
 * @author nikelin
 * @date 21/04/11
 * @package com.redshape.ascript.evaluation.functions.language
 */
public class NotDeclaredFunction extends Lambda<Object> {
	private IEvaluator evaluator;

	public NotDeclaredFunction( IEvaluator evaluatorContext ) {
		this.evaluator = evaluatorContext;
	}

	@Override
	public Object invoke(Object... arguments) throws InvocationException {
		this.assertArgumentsCount( arguments, 1 );
		this.assertArgumentType( arguments[0], String.class );

	   return this.evaluator.getRootContext().get( String.valueOf( arguments[0] ) );
	}

}
