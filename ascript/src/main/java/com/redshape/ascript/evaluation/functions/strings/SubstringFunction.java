package com.redshape.ascript.evaluation.functions.strings;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;

/**
 * @author nikelin
 * @date 25/04/11
 * @package com.redshape.ascript.evaluation.functions.strings
 */
public class SubstringFunction extends Lambda<String> {
    private IEvaluator evaluator;

    public SubstringFunction( IEvaluator evaluator ) {
        this.evaluator = evaluator;
    }

    @Override
    public String invoke( Object... args ) throws InvocationException {
        if ( args.length != 2 && args.length != 3 ) {
            throw new IllegalArgumentException("Wrong arguments count!");
        }

        this.assertArgumentType( args[1], Double.class );
        this.assertArgumentType( args[2], Double.class );

        final String string = (String) args[0];
        final Integer from = (Integer) args[1];
        final Integer to = (Integer) args[2] ;

        return string.substring( from, to );
    }

}
