package com.redshape.server.policy;

import com.redshape.server.IServer;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 18, 2010
 * Time: 4:22:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class PoliciesFactory {
    private static PoliciesFactory defaultInstance = new PoliciesFactory();

    public static PoliciesFactory getDefault() {
        return defaultInstance;
    }

    public static void setDefault( PoliciesFactory instance ) {
        defaultInstance = instance;
    }

    public IPolicy createPolicy( Class<? extends IPolicy> policyClazz, IServer server ) throws InstantiationException {
        IPolicy policy;
        try {
            policy = policyClazz.newInstance();
        } catch( Throwable e ) {
            throw new InstantiationException();
        }

        policy.setContext( server );

        return policy;
    }
}
