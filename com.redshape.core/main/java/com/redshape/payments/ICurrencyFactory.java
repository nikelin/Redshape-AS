package com.redshape.payments;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 10, 2010
 * Time: 11:13:15 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ICurrencyFactory<T extends ICurrencyAmount, V extends Number> {

    public ICurrencyAmount<T, V> createAmount( V amount );

}
