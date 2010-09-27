package com.redshape.validators;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Sep 21, 2010
 * Time: 7:44:27 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IMapValidator<T extends Map> {

    public void addCondition( String path, IValidator validator );

    public boolean isValid( T object );

}
