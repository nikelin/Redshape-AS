package com.redshape.validators;

import com.redshape.validators.result.IValidationResult;
import com.redshape.validators.result.ValidationResult;

public interface IValidator<T, R extends IValidationResult> {

	public boolean isValid( T value );

	public R validate( T value );
	
}
