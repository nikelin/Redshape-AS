package com.redshape.payments;

import com.redshape.utils.CompareResult;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 10, 2010
 * Time: 7:36:06 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ICurrencyAmount<T extends ICurrencyAmount, V extends Number> {

    public Boolean isEnough( T amount );

    public Boolean isEnough( V amount );

    public Boolean isNull();

    public CompareResult compare( T comparable );

    public void increase( T amount );

    public void decrease( T amount );

    public String getString();

    public V getValue();

    public void setValue( V value );

}
