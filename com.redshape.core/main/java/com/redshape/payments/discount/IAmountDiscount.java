package com.redshape.payments.discount;

import com.redshape.payments.ICurrencyAmount;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 11, 2010
 * Time: 12:02:54 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IAmountDiscount extends IDiscount {

    public ICurrencyAmount getAmount();
    
}
