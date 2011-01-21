package com.redshape.payments;

import com.redshape.payments.entities.PayingException;


/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 8, 2010
 * Time: 2:43:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPayingPolicy {

    public boolean isBalanceUsage();

    public void doBalanceUsage( boolean state );

    public void doDiscountsUsage( boolean state );

    public boolean isDiscountsUsage();

    public void pay( IPayable payable ) throws PayingException;
}
