package com.redshape.ascript.evaluation.functions.language;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;

/**
 * @author nikelin
 * @date 25/04/11
 * @package com.redshape.ascript.evaluation.functions.language
 */
public class ClassFunction extends Lambda<Class<?>> {
    private IEvaluator evaluator;

    public ClassFunction( IEvaluator evaluator ) {
        this.evaluator = evaluator;
    }

    @Override
    public Class<?> invoke( Object... args ) throws InvocationException {
        this.assertArgumentsCount( args, 1 );
        this.assertArgumentsType( args, String.class );

        try {
            return Class.forName( String.valueOf( args[0] ) );
        } catch ( Throwable e ) {
            throw new InvocationException( e.getMessage(), e );
        }
    }

}
