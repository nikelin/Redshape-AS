package com.redshape.validators.common;

import com.redshape.validators.IValidator;
import com.redshape.validators.result.ValidationResult;

public class NumericStringValidator implements IValidator<String, ValidationResult> {

	@Override
	public ValidationResult validate(String value) {
		return new ValidationResult( this.isValid(value) );
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
