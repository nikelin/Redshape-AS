package com.redshape.payments.discount;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 11, 2010
 * Time: 12:05:35 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ICountableDiscount extends IDiscount {

    public Integer getMaxActivations();

    public Integer getAmount();

}
