package com.redshape.payments;

import com.redshape.persistence.entities.payment.BalanceOperations;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 10, 2010
 * Time: 5:23:21 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IBalanceOperation {

    public void setType( BalanceOperations type );

    public BalanceOperations getType();

    public ICurrencyAmount getAmount();

    public void setAmount( ICurrencyAmount amount );

    public Long getDate();

    public void setDate( Long date );

}
