package com.redshape.utils.validators;

import com.redshape.utils.validators.result.IValidationResult;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/22/12
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class ValidationException extends Exception {

    private IValidationResult result;

    public ValidationException(IValidationResult result) {
        this(result.getMessage());

        this.result = result;
    }

    public ValidationException() {
        this( (String) null );
    }

    public ValidationException(String message) {
        this(message, null);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        return "ValidationException{result=" + result + "}";
    }
}
