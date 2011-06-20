package com.redshape.validators.impl.common;

import com.redshape.validators.result.IValidationResult;

public class ValidationResult implements IValidationResult {

	private boolean state;
	private String message;
	
	public ValidationResult( boolean state, String message ) {
		this.state = state;
		this.message = message;
	}
	
	@Override
	public boolean isValid() {
		return this.state;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
