package com.vio.features.validators;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 6, 2010
 * Time: 12:24:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IFeatureValidator<T> {

    public boolean isValid( T validatable ); 

}
