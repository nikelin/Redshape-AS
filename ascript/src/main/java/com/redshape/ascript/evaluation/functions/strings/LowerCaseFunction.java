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
public class LowerCaseFunction extends Lambda<String> {
    private IEvaluator evaluator;

    public LowerCaseFunction( IEvaluator evaluator ) {
        this.evaluator = evaluator;
    }

    @Override
    public String invoke( Object... args ) throws InvocationException {
        this.assertArgumentsCount( args, 1 );
        this.assertArgumentsType( args, String.class );

        return ( (String) args[0] ).toLowerCase();
    }

}
