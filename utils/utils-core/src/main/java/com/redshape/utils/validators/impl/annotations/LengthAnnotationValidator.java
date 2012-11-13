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

package com.redshape.utils.validators.impl.annotations;

import com.redshape.utils.AnnotatedObject;
import com.redshape.utils.validators.annotations.Length;
import com.redshape.utils.validators.impl.annotations.result.ValidationResult;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators.impl
 */
public class LengthAnnotationValidator extends AbstractAnnotationValidator<AnnotatedObject, ValidationResult> {

    public LengthAnnotationValidator() {
        super(Length.class);
    }

    @Override
	public boolean isValid(AnnotatedObject value) {
		Length annotation = value.getAnnotation( Length.class );
		if ( !( value.getContext() instanceof String ) ) {
			return true;
		}

		return annotation.max() >= value.<String>getContext().length()
				&& value.<String>getContext().length() >= annotation.min();

	}
	
	@Override
	public ValidationResult validate(AnnotatedObject value) {
		return new ValidationResult( this.isValid(value) );
	}
}
