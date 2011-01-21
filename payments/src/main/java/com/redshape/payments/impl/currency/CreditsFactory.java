package com.redshape.payments.impl.currency;

import com.redshape.payments.entities.payment.impl.CreditsAmount;


/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 10, 2010
 * Time: 11:16:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class CreditsFactory extends CurrencyFactory<CreditsAmount, Double> {

    public CreditsAmount createAmount( Double amount ) {
        return new CreditsAmount(amount);
    }

}
