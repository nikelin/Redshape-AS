package com.redshape.ascript.evaluation.functions.strings;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.Lambda;

import java.util.Arrays;
import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.ascript.evaluation.functions.strings
 * @date 8/13/11 11:37 PM
 */
public class SplitFunction extends Lambda<List<String>> {
    private IEvaluator evaluator;

    public SplitFunction( IEvaluator evaluator ) {
        this.evaluator = evaluator;
    }

    @Override
    public List<String> invoke( Object... args ) {
        this.assertArgumentsCount( args, 2 );
        this.assertArgumentsType( args, String.class );

        return Arrays.asList((( String ) args[0]).split(( String ) args[1]));
    }

}
