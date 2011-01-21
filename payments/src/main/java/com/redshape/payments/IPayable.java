package com.redshape.payments;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 8, 2010
 * Time: 2:42:41 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPayable<T extends ICurrencyAmount> {

    public T getPrice();

    public Boolean isDiscountable();

    public void setDiscountable( Boolean state );

}
