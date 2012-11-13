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

package com.redshape.utils.validators.simple;

import com.redshape.utils.validators.AbstractValidator;
import com.redshape.utils.validators.result.ValidationResult;

public class NotEmptyValidator extends AbstractValidator<String, ValidationResult> {

	@Override
	public ValidationResult validate(String value) {
		return new ValidationResult( this.isValid(value), "Non-empty value expected!");
	}

	@Override
	public boolean isValid(String value) {
		return value != null && !value.isEmpty();
	}
	
}
