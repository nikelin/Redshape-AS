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

package com.redshape.ascript.evaluation.functions.arrays;

import com.redshape.ascript.IEvaluator;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;

import java.util.List;

/**
 * @author nikelin
 * @date 20/04/11
 * @package com.redshape.ascript.evaluation.functions.arrays
 */
public class CDRFunction extends Lambda<List<?>> {

	private IEvaluator evaluator;

	public CDRFunction( IEvaluator evaluatorContext ) {
		this.evaluator = evaluatorContext;
	}

	@Override
	public List<?> invoke( Object... args ) throws InvocationException {
		try {
			this.assertArgumentsCount( args, 1 );
			this.assertArgumentType( args[0], List.class );

			return ( (List) args[0] ).subList( 1, ( (List) args[0]).size() );
		} catch ( Throwable e ) {
			throw new InvocationException( e.getMessage(), e );
		}
	}
}
