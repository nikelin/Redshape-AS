package com.redshape.validators;

import com.redshape.validators.result.ValidationResult;

public class NotEmptyValidator implements IValidator<String, ValidationResult> {

	@Override
	public ValidationResult validate(String value) {
		return new ValidationResult(value);
	}

	@Override
	public boolean isValid(String value) {
		return value != null && !value.isEmpty();
	}
	
}
