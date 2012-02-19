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
