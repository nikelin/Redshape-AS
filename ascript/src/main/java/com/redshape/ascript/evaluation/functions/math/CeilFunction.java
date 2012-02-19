package com.redshape.ascript.evaluation.functions.math;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.Lambda;

/**
 * @author nikelin
 * @date 25/04/11
 * @package com.redshape.ascript.evaluation.functions.math
 */
public class CeilFunction extends Lambda<Integer> {
    private IEvaluator evaluator;

    public CeilFunction( IEvaluator evaluator ) {
        this.evaluator = evaluator;
    }

    @Override
    public Integer invoke( Object... args ) {
        this.assertArgumentsCount( args, 1 );
        this.assertArgumentsType( args, Number.class );

        return ( (Number) args[0] ).intValue();
    }

}
