package com.redshape.validators;

/**
 * @author nikelin
 */
public interface IValidator<T> {

    public boolean isValid( T object );

}