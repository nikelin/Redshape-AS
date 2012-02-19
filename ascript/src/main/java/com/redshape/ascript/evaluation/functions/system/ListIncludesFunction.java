package com.redshape.ascript.evaluation.functions.system;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 4/24/11
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListIncludesFunction extends Lambda<Collection<String>> {
    private IEvaluator evaluator;

    public ListIncludesFunction( IEvaluator evaluator ) {
        this.evaluator = evaluator;
    }

    @Override
    public Collection<String> invoke( Object... args ) throws InvocationException {
        return this.evaluator.getIncludes();
    }

}
