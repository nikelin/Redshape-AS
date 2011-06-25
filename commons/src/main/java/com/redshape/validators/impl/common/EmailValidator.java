package com.redshape.validators.impl.common;

import com.redshape.validators.AbstractValidator;
import com.redshape.validators.result.IValidationResult;

import java.util.regex.Pattern;

public class EmailValidator extends AbstractValidator<String, IValidationResult> {
	private Pattern pattern = Pattern.compile("\\w+@(\\w+\\.\\w+){1,}");
	
	@Override
	public boolean isValid(String value) {
		return ( value == null || !value.isEmpty() ) || this.pattern.matcher( value ).find();
	}

	@Override
	public IValidationResult validate(String value) {
		return new ValidationResult( this.isValid(value), "Invalid e-mail address format");
	}

}
