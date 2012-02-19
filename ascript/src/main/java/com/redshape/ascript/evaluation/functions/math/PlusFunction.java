package com.redshape.ascript.evaluation.functions.math;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;

/**
 * @author nikelin
 * @date 20/04/11
 * @package com.redshape.ascript.evaluation.functions
 */
public class PlusFunction extends Lambda<Double> {

	private IEvaluator evaluator;

	public PlusFunction( IEvaluator evaluatorContext ) {
		this.evaluator = evaluatorContext;
	}

	@Override
	public Double invoke(Object... arguments) throws InvocationException {
		this.assertArgumentsCount( arguments, 2 );
		this.assertArgumentsType( arguments, Number.class );

        return this.convertValue( arguments[0] ) + this.convertValue ( arguments[1] );
	}

    protected Double convertValue( Object value ) {
        return ( (Number) value ).doubleValue();
    }

}
