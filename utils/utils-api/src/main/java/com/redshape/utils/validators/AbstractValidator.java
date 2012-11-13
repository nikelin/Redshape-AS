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

package com.redshape.utils.validators;

import com.redshape.utils.validators.result.IValidationResult;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractValidator<T, R extends IValidationResult> implements IValidator<T, R> {
	private Map<String, Object> attributes = new HashMap<String, Object>();
    
	@Override
	public void setAttribute(String name, Object value) {
		if ( name == null ) {
			throw new IllegalArgumentException("<null>");
		}
		
		this.attributes.put(name, value);
	}
	
	@SuppressWarnings("unchecked")
	protected <V> V getAttribute( String name ) {
		return (V) this.attributes.get( name );
	}
	
	protected Map<String, Object> getAttributes() {
		return this.attributes;
	}
	
}
