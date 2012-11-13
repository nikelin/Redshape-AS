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
