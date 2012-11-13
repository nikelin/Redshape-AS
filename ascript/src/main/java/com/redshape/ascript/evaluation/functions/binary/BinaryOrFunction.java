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

package com.redshape.ascript.evaluation.functions.binary;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;

/**
 * @author nikelin
 * @date 20/04/11
 * @package com.redshape.ascript.evaluation.functions.binary
 */
public class BinaryOrFunction extends Lambda<Integer> {

	private IEvaluator evaluator;

	public BinaryOrFunction( IEvaluator evaluatorContext ) {
		this.evaluator = evaluatorContext;
	}

	@Override
	public Integer invoke(Object... arguments) throws InvocationException {
		this.assertArgumentsCount( arguments, 2 );
		this.assertArgumentType( arguments[0], Integer.class );
		this.assertArgumentType( arguments[1], Integer.class );

		return (Integer) arguments[0] | (Integer) arguments[1];
	}
}
