package com.redshape.payments;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Oct 1, 2010
 * Time: 3:17:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPaymentMethod {

    public void setStatus( boolean status );

    public boolean isEnabled();

    public String getName();

}
