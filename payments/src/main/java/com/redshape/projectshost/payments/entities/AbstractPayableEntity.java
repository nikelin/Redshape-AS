package com.redshape.projectshost.payments.entities;

import com.redshape.projectshost.payments.ICurrencyAmount;
import com.redshape.projectshost.payments.IPayable;
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
public abstract class AbstractPayableEntity<V extends ICurrencyAmount<V, ?>>
						extends AbstractEntity 
						implements IPayable<V> {
	private static final long serialVersionUID = -111976587910694505L;

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
