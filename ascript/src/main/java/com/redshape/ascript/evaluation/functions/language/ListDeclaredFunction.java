/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.ascript.evaluation.functions.language;

import com.redshape.ascript.EvaluationException;
import com.redshape.ascript.IEvaluator;
import com.redshape.ascript.context.items.FunctionItem;
import com.redshape.utils.IFunction;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * @author nikelin
 * @date 25/04/11
 * @package com.redshape.ascript.evaluation.functions.language
 */
public class ListDeclaredFunction extends Lambda<Collection<String>> {
    private IEvaluator evaluator;

    public ListDeclaredFunction( IEvaluator evaluator ) {
        this.evaluator = evaluator;
    }

    @Override
    public Collection<String> invoke( Object... args ) throws InvocationException {
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
            throw new InvocationException( e.getMessage(), e );
        }

        return result;
    }

}
