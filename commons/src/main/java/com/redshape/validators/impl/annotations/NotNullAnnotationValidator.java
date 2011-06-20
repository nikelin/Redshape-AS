package com.redshape.validators.impl.annotations;

import com.redshape.utils.AnnotatedObject;
import com.redshape.validators.AbstractValidator;
import com.redshape.validators.impl.annotations.result.ValidationResult;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators.impl
 */
public class NotNullAnnotationValidator extends AbstractValidator<AnnotatedObject, ValidationResult> {

	@Override
	public boolean isValid(AnnotatedObject value) {
		return value != null;
	}

	@Override
	public ValidationResult validate(AnnotatedObject value) {
		return new ValidationResult( this.isValid(value) );
	}
}
