package com.redshape.ascript.evaluation.functions.language;

import com.redshape.ascript.EvaluationException;
import com.redshape.ascript.IEvaluator;
import com.redshape.ascript.context.items.FunctionItem;
import com.redshape.utils.Function;
import com.redshape.utils.IFunction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * @author nikelin
 * @date 25/04/11
 * @package com.redshape.ascript.evaluation.functions.language
 */
public class ListDeclaredFunction extends Function<Object, Collection<String>> {
    private IEvaluator evaluator;

    public ListDeclaredFunction( IEvaluator evaluator ) {
        this.evaluator = evaluator;
    }

    @Override
    public Collection<String> invoke( Object... args ) throws InvocationTargetException {
        Collection<String> result = new HashSet<String>();

        try {
            Map<String, FunctionItem> functions = this.evaluator.getRootContext().listFunctions();
            for ( String key : functions.keySet() ) {
                StringBuilder nameBuilder = new StringBuilder();

                try {
                    Method method = functions.get(key).<IFunction<?,?>>getValue().toMethod();

                    nameBuilder.append( "(" )
                           .append(key)
                           .append(":")
                           .append(method.getReturnType().getCanonicalName())
                           .append(" with ")
                           .append(method.getParameterTypes().length)
                           .append(" arguments ")
                           .append(")");
                } catch ( Throwable e ) {
                    continue;
                }

                result.add( nameBuilder.toString() );
            }
        } catch ( EvaluationException e ) {
            throw new InvocationTargetException( e );
        }

        return result;
    }

}
