package com.redshape.ascript.evaluation.functions.strings;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;

/**
 * @author nikelin
 * @date 25/04/11
 * @package com.redshape.ascript.evaluation.functions.strings
 */
public class UpperCaseFunction extends Lambda<String> {
    private IEvaluator evaluator;

    public UpperCaseFunction( IEvaluator evaluator ) {
        this.evaluator = evaluator;
    }

    @Override
    public String invoke( Object... args ) throws InvocationException {
        this.assertArgumentsCount( args, 1 );
        this.assertArgumentsType( args, String.class );

        return ( (String) args[0] ).toUpperCase();
    }

}
