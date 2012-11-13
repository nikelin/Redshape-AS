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
import com.redshape.ascript.context.IEvaluationContextItem;
import com.redshape.utils.ILambda;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;
import com.redshape.utils.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author nikelin
 * @date 20/04/11
 * @package com.redshape.ascript.expressions.context.items
 */
public class ClassContext implements IEvaluationContextItem {
	private Class<?> clazz;

	public ClassContext( Class<?> clazz ) {
		this.clazz = clazz;
	}

	@Override
	public <T> ILambda<T> getMethod(String name, int argumentsCount, Class<?>[] types ) throws EvaluationException {
		for ( final Method method : this.clazz.getMethods() ) {
			if ( method.getName().equals(name) && method.getParameterTypes().length == argumentsCount
					&& ReflectionUtils.compareTypeLists(method.getParameterTypes(), types) ) {
				return new Lambda<T>() {
                    @Override
                    public T invoke(Object... object) throws InvocationException {
                        try {
                            return (T) method.invoke(ClassContext.this.clazz);
                        } catch ( Throwable e ) {
                            throw new InvocationException( e.getMessage(), e );
                        }
                    }
                };
			}
		}

		return null;
	}

	@Override
	public <V> V getValue(String name) throws EvaluationException {
		try {
			return (V) this.clazz.getField(name).get( this.clazz );
		} catch ( Throwable e ) {
			throw new EvaluationException( e.getMessage(), e );
		}
	}

	@Override
	public <V> V getValue() throws EvaluationException {
		return (V) this.clazz;
	}
}
