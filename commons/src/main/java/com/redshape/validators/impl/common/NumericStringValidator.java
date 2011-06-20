package com.redshape.validators.impl.common;

import com.redshape.validators.AbstractValidator;
import com.redshape.validators.impl.annotations.result.ValidationResult;

public class NumericStringValidator extends AbstractValidator<String, ValidationResult> {

	@Override
	public ValidationResult validate(String value) {
		return new ValidationResult( this.isValid(value), "Numeric value expected!" );
	}

	@Override
	public boolean isValid( String value ) {
		try {
			return value != null && !value.isEmpty() && Integer.valueOf(value) != null;
		} catch ( NumberFormatException e ) {
			return false;
		}
	}
	
}
