package com.redshape.projectshost.payments;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Oct 1, 2010
 * Time: 3:06:01 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPayment<T extends IPayer, V extends IPayable, M extends IPaymentMethod> {

    public void setCustomer( T customer );

    public T getCustomer();

    public void setProduct( V product );

    public V getProduct();

    public void setPaymentMethod( M method );

    public M getPaymentMethod();

    public Date getDate();

    public void setDate( Date date );

    public void setValidUntil( Date date );

    public Date getValidUntil();

    public boolean isValid();

    public void changeState( PaymentState state );

    public PaymentState getState();

}
