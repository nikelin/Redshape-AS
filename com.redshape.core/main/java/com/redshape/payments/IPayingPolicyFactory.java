package com.redshape.payments;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 10, 2010
 * Time: 9:26:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPayingPolicyFactory {

    public IPayingPolicy getPolicy( IPayer payer );

}
