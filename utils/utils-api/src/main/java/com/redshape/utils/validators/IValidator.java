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

/**
 * Validator interface to provide custom validation logic includes
 * simple boolean validation and complex validation with required
 * some type of the result object.
 *
 * @see com.redshape.utils.validators.result.IResultsList
 * 
 * @author nikelin
 *
 * @param <T>
 * @param <R>
 */
public interface IValidator<T, R extends IValidationResult> {

	public void setAttribute( String name, Object value );

    public boolean isValid(T value);

    public R validate( T value );
	
}
