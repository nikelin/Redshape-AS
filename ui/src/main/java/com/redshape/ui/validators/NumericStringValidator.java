package com.redshape.ui.validators;

public class NumericStringValidator implements IValidator<String> {
	
	@Override
	public boolean isValid( String value ) {
		try {
			return value != null && !value.isEmpty() && Integer.valueOf(value) != null;
		} catch ( NumberFormatException e ) {
			return false;
		}
	}
	
}
