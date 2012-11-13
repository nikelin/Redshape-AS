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

package com.redshape.utils.validators.impl.annotations.result;

import com.redshape.utils.validators.result.IValidationResult;

import java.lang.annotation.Annotation;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators
 */
public class ValidationResult implements IValidationResult {
	private String name;
	private String message;
	private Annotation subject;
	private boolean success;

	public ValidationResult( boolean success, String message ) {
		this.success = success;
		this.message = message;
	}

	public ValidationResult( boolean success ) {
		this(null, null, success);
	}
	
	public ValidationResult( String name ) {
		this( name, null, true );
	}

	public ValidationResult( String name, Annotation subject, boolean success) {
		this.name = name;
		this.subject = subject;
		this.success = success;
	}

	public String getMessage() {
		return this.message;
	}
	
	public Annotation getSubject() {
		return this.subject;
	}

	public String getName() {
		return this.name;
	}

	public boolean isValid() {
		return this.success;
	}

}
