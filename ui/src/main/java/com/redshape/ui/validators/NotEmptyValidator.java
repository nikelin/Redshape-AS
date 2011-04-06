package com.redshape.ui.validators;

public class NotEmptyValidator implements IValidator<String> {

	@Override
	public boolean isValid(String value) {
		return value != null && !value.isEmpty();
	}
	
}
