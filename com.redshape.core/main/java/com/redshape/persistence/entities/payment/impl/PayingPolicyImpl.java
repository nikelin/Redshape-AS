package com.redshape.persistence.entities.payment.impl;

import com.redshape.payments.ICurrencyAmount;
import com.redshape.payments.IPayable;
import com.redshape.payments.IPayer;
import com.redshape.payments.IPayingPolicy;
import com.redshape.persistence.entities.payment.*;
import com.redshape.payments.discount.IDiscount;
import com.redshape.persistence.entities.payment.impl.currency.CurrencyFactory;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 10, 2010
 * Time: 9:23:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class PayingPolicyImpl implements IPayingPolicy {
    private boolean balanceUsage;
    private boolean discountsUsage;
    private IPayer payer;

    public PayingPolicyImpl( IPayer payer ) {
        this.payer = payer;
    }

    public boolean isBalanceUsage() {
        return this.balanceUsage;
    }

    public void doBalanceUsage( boolean state ) {
        this.balanceUsage = state;
    }

    public void doDiscountsUsage( boolean state ) {
        this.discountsUsage = state;
    }

    public boolean isDiscountsUsage() {
        return this.discountsUsage;
    }

    public void pay( IPayable payable ) throws PayingException {
        ICurrencyAmount amount = payable.getPrice();

        if ( this.isDiscountsUsage() && this.getPayer().hasDiscounts() ) {
            amount.decrease( this.useDiscounts( payable ) );
        }

        if ( amount.isNull() ) {
            return;
        }

        if ( !this.isBalanceUsage() || !this.payer.getBalance().isEnough(amount) ) {
            throw new PayingException("You do not have such credits amount");
        }

        this.getPayer().getBalance().decrease( amount );
    }

    protected IPayer getPayer() {
        return this.payer;
    }

    /**
     * @TODO
     * @param payable
     * @return
     * @throws PayingException
     */
    protected ICurrencyAmount useDiscounts( IPayable payable ) throws PayingException {
        ICurrencyAmount result = CurrencyFactory.getDefault().createAmount( payable.getPrice().getValue() );

        for ( IDiscount discount : this.payer.getDiscounts() ) {
            discount.applicate( payable );
        }

        return result;
    }
}
