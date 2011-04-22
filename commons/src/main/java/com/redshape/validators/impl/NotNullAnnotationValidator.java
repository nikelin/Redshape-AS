package com.redshape.validators.impl;

import com.redshape.validators.AnnotatedObject;
import com.redshape.validators.IValidator;
import com.redshape.validators.result.ValidationResult;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators.impl
 */
public class NotNullAnnotationValidator implements IValidator<AnnotatedObject, ValidationResult> {

	@Override
	public boolean isValid(AnnotatedObject value) {
		return value != null;
	}

	@Override
	public ValidationResult validate(AnnotatedObject value) {
		return new ValidationResult( this.isValid(value) );
	}
}
