package com.redshape.validators.impl.common;

import com.redshape.validators.AbstractValidator;
import com.redshape.validators.impl.annotations.result.ValidationResult;

import java.util.regex.Pattern;

public class NumericStringValidator extends AbstractValidator<String, ValidationResult> {
	private static final Pattern pattern = Pattern.compile("^[0-9]+$");

	@Override
	public ValidationResult validate(String value) {
		return new ValidationResult( this.isValid(value), "Numeric value expected!" );
	}

	@Override
	public boolean isValid( String value ) {
		return value == null || value.isEmpty() || pattern.matcher(value).find();
	}
	
}
