package com.redshape.projectshost.payments;

import com.redshape.projectshost.payments.discount.IDiscount;
import com.redshape.projectshost.payments.entities.PayingException;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 10, 2010
 * Time: 3:34:38 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPayer {

    public void pay( IPayable item ) throws PayingException;

    public Set<IDiscount> getDiscounts();

    public Boolean hasDiscounts();

    public void addDiscount( IDiscount discount );

    public void removeDiscount( IDiscount discount );

    public IBalance getBalance();

}
