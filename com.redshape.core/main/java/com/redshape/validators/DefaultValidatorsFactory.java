package com.redshape.validators;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 11, 2010
 * Time: 1:50:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultValidatorsFactory extends ValidatorsFactory {

    public IValidator forEntity( Class<?> entity ) throws InstantiationException {
        try {
            return  (IValidator) this.getClass().getMethod("forEntity", entity ).invoke( this, entity );
        } catch ( Throwable e ) {
            throw new InstantiationException("Method to create validator for given entity is not exists.");
        }
    }

}
