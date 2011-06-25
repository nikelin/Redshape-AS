package com.redshape.validators.impl.common;

import com.redshape.validators.AbstractValidator;

import java.util.regex.Pattern;

public class URLValidator extends AbstractValidator<String, ValidationResult> {
	private Pattern pattern =
		Pattern.compile("((https?|ftp|gopher|telnet|file|notes|ms-help|mailto|magnet|):((\\/\\/)|\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\.&]*");
	
	@Override
	public boolean isValid(String value) {
		return value == null ||  !value.isEmpty() || this.pattern.matcher(value).find();
	}

	@Override
	public ValidationResult validate(String value) {
		return new ValidationResult( this.isValid(value), "Invalid URL format!");
	}

}
