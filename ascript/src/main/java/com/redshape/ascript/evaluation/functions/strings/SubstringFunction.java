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
