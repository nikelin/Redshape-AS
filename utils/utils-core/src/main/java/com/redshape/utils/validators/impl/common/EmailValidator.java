package com.redshape.utils.validators.impl.common;

import com.redshape.utils.validators.AbstractValidator;
import com.redshape.utils.validators.result.ValidationResult;

import java.util.regex.Pattern;

public class EmailValidator extends AbstractValidator<String, ValidationResult> {
	private Pattern pattern = Pattern.compile("\\w+@(\\w+\\.\\w+){1,}");
	
	@Override
	public boolean isValid(String value) {
		return ( value == null || !value.isEmpty() ) || this.pattern.matcher( value ).find();
	}

	@Override
	public ValidationResult validate(String value) {
		return new ValidationResult( this.isValid(value), "Invalid e-mail address format");
	}

}
