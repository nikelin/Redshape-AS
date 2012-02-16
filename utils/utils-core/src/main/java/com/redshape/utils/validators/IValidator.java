package com.redshape.utils.validators;

import com.redshape.utils.validators.result.IValidationResult;

/**
 * Validator interface to provide custom validation logic includes
 * simple boolean validation and complex validation with required
 * some type of the result object.
 *
 * @see com.redshape.utils.validators.result.IResultsList
 * 
 * @author nikelin
 *
 * @param <T>
 * @param <R>
 */
public interface IValidator<T, R extends IValidationResult> {

	public void setAttribute( String name, Object value );

    public boolean isValid(T value);

    public R validate( T value );
	
}
