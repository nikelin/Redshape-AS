package com.redshape.validators.impl.common;

import com.redshape.validators.AbstractValidator;
import com.redshape.validators.impl.annotations.result.ValidationResult;

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
