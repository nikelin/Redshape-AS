package com.redshape.ascript.evaluation.functions.strings;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.Lambda;

/**
 * @author nikelin
 * @date 25/04/11
 * @package com.redshape.ascript.evaluation.functions.strings
 */
public class IndexOfFunction extends Lambda<Integer> {
    private IEvaluator evaluator;

    public IndexOfFunction( IEvaluator evaluator ) {
        this.evaluator = evaluator;
    }

    @Override
    public Integer invoke( Object... args ) {
        this.assertArgumentsCount( args, 2 );
        this.assertArgumentsType( args, String.class );

        return ( (String) args[0] ).indexOf( (String) args[1] );
    }

}
