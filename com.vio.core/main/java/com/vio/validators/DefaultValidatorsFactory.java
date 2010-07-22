package com.vio.validators;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 11, 2010
 * Time: 1:50:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultValidatorsFactory extends ValidatorsFactory {

    public Validator forEntity( Class<?> entity ) throws InstantiationException {
        try {
            return  (Validator) this.getClass().getMethod("forEntity", entity ).invoke( this, entity );
        } catch ( Throwable e ) {
            throw new InstantiationException("Method to create validator for given entity is not exists.");
        }
    }

}
