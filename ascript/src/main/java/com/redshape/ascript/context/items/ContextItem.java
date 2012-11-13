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

package com.redshape.ascript.context.items;

import com.redshape.ascript.EvaluationException;
import com.redshape.ascript.context.IEvaluationContext;
import com.redshape.ascript.context.IEvaluationContextItem;
import com.redshape.utils.ILambda;

import java.lang.reflect.Method;

public class ContextItem implements IEvaluationContextItem {
	private IEvaluationContext context;
	
	public ContextItem( IEvaluationContext context ) {
		this.context = context;
	}

	@Override
	public <V> V getValue(String name) throws EvaluationException {
		return (V) this.context.get(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getValue() throws EvaluationException {
		return (V) this.context;
	}

	@Override
	public <T> ILambda<T> getMethod(String name, int argumentsCount, Class<?>[] types ) throws EvaluationException {
		return this.context.resolveFunction(name, argumentsCount, types);
	}
	
}
