package com.redshape.persistence.entities.payment;

import com.redshape.payments.ICurrencyAmount;
import com.redshape.persistence.entities.AbstractEntity;
import com.redshape.utils.CompareResult;

import javax.persistence.Basic;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 10, 2010
 * Time: 7:38:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity( name = "credits_amount" )
public class CreditsAmount extends AbstractEntity<CreditsAmount>
                        implements ICurrencyAmount<CreditsAmount, Double>, Serializable {
    @Basic
    private Double amount;

    public CreditsAmount() {
        super();
    }

    public CreditsAmount( Double amount ) {
        this.amount = amount;
    }

    public String getString() {
        return String.valueOf( amount );
    }

    public void decrease( CreditsAmount amountObject ) {
        this.amount -= amountObject.amount;
    }

    public void increase( CreditsAmount amountObject ) {
        this.amount += amountObject.amount;
    }

    public Boolean isEnough( CreditsAmount amountObject ) {
        return this.amount >= amountObject.amount;
    }

    public Boolean isEnough( Double value ) {
        return this.amount >= value;
    }

    public Boolean isNull() {
        return this.amount == 0;
    }

    public Double getValue() {
        return this.amount;
    }

    public void setValue( Double value ) {
        this.amount = value;
    }

    public CompareResult compare( CreditsAmount amount ) {
        return ( this.amount == amount.amount ? CompareResult.EQUALS :
                    ( amount.isNull() ? CompareResult.NULL :
                        ( amount.amount > this.amount ? CompareResult.GREATER :
                            CompareResult.LESS ) ) );
    }

}
