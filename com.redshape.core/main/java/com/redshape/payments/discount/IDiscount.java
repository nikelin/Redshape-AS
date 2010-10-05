package com.redshape.payments.discount;

import com.redshape.payments.IPayable;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 10, 2010
 * Time: 4:34:30 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IDiscount {

    public String getName();

    public Boolean isApplicable( IPayable payable );

    public void applicate( IPayable payable );

    public Boolean isApplicated();
}
