package com.redshape.ascript.evaluation.functions.strings;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.Function;

/**
 * @author nikelin
 * @date 25/04/11
 * @package com.redshape.ascript.evaluation.functions.strings
 */
public class ConcatFunction extends Function {
    private IEvaluator evaluator;

    public ConcatFunction( IEvaluator evaluator ) {
        this.evaluator = evaluator;
    }

    @Override
    public String invoke( Object... args ) {
        this.assertArgumentsType( args, String.class );

        StringBuilder result = new StringBuilder();
        for ( Object arg : args ) {
            result.append( String.valueOf( arg ) );
        }

        return result.toString();
    }

}
