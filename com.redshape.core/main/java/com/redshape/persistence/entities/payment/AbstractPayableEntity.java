package com.redshape.persistence.entities.payment;

import com.redshape.payments.ICurrencyAmount;
import com.redshape.payments.IPayable;
import com.redshape.persistence.entities.AbstractEntity;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 10, 2010
 * Time: 10:42:07 PM
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public class AbstractPayableEntity<T extends AbstractEntity, V extends ICurrencyAmount> extends AbstractEntity<T> implements IPayable<V> {

    @Basic
    private Boolean isDiscountable;

    private V price;

    public V getPrice() {
        return this.price;
    }

    public Boolean isDiscountable() {
        return this.isDiscountable;
    }

    public void setDiscountable( Boolean state ) {
        this.isDiscountable = state;
    }

}
