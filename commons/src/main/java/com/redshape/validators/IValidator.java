package com.redshape.validators;

import com.redshape.validators.result.IValidationResult;

/**
 * Validator interface to provide custom validation logic includes
 * simple boolean validation and complex validation with required
 * some type of the result object.
 * 
 * @see com.redshape.validators.common.validations.BeansValidator
 * @see com.redshape.validators.result.IResultsList
 * 
 * @author nikelin
 *
 * @param <T>
 * @param <R>
 */
public interface IValidator<T, R extends IValidationResult> {

	public boolean isValid( T value );

	public R validate( T value );
	
}
