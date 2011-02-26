package com.redshape.payments;

import com.redshape.payments.entities.BalanceOperations;


/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 10, 2010
 * Time: 5:23:21 PM
 * To change this template use File | Settings | File Templates.
 */
// @TODO: refactor to remove generics
public interface IBalanceOperation<T extends ICurrencyAmount<T, V>, V extends Number> {

    public void setType( BalanceOperations type );

    public BalanceOperations getType();

    public ICurrencyAmount<T, V> getAmount();

    public void setAmount( ICurrencyAmount<T, V> amount );

    public Long getDate();

    public void setDate( Long date );

}
