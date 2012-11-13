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

package com.redshape.ascript.evaluation.functions.utils;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.Lambda;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nikelin
 * @date 25/04/11
 * @package com.redshape.ascript.evaluation.functions.utils
 */
public class CounterFunction extends Lambda<Integer> {
    private IEvaluator evaluator;

    private static Map<String, Integer> counters = new HashMap<String, Integer>();
    private static Map<String, Integer> limits = new HashMap<String, Integer>();

    public CounterFunction( IEvaluator evaluator ) {
        this.evaluator = evaluator;
    }

    @Override
    public Integer invoke( Object... args ) {
        this.assertArgumentType( args[0], String.class );

        boolean created = false;

        final String counterName = (String) args[0];
        if ( !counters.containsKey(counterName) ) {
            counters.put( counterName, 0 );
            created = true;
        }

        final Integer limit;
        if ( args.length > 1 ) {
            this.assertArgumentType( args[1], Double.class );
            this.limits.put( counterName,  limit = ( (Double) args[1] ).intValue() );
        } else {
            limit = this.limits.get( counterName );
        }

        if ( created ) {
            return 0;
        }

        if ( limit != null && counters.get(counterName) >= limit ) {
            counters.remove(counterName);
            return limit;
        }

        Integer currentValue;
        counters.put( counterName, currentValue = counters.get(counterName) + 1 );

        return currentValue;
    }

}
