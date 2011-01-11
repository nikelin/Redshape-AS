package com.redshape.persistence.entities.payment;

import com.redshape.payments.IPayer;
import com.redshape.payments.IPayingPolicy;
import com.redshape.payments.IPayingPolicyFactory;
import com.redshape.persistence.entities.payment.impl.PayingPolicyImpl;

import org.apache.commons.collections.map.MultiKeyMap;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 10, 2010
 * Time: 9:28:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class PayingPolicyFactory implements IPayingPolicyFactory {
    private static IPayingPolicyFactory defaultInstance = new PayingPolicyFactory();
    
    private MultiKeyMap policies = new MultiKeyMap();

    public static IPayingPolicyFactory getDefault() {
        return defaultInstance;
    }

    public static void setDefault( IPayingPolicyFactory instance ) {
        defaultInstance = instance;
    }

    public IPayingPolicy getPolicy( IPayer payer ) {
        IPayingPolicy policy = this._getPolicy(payer);
        if ( policy != null ) {
            return policy;
        }

        policy = this.createPolicy(payer);

        this.policies.put( Thread.currentThread().getId(), payer, policy );

        return policy;
    }

    private IPayingPolicy _getPolicy( IPayer payer ) {
        return (IPayingPolicy) this.policies.get( Thread.currentThread().getId(), payer );
    }

    protected IPayingPolicy createPolicy( IPayer payer ) {
        return new PayingPolicyImpl(payer);
    }

}
