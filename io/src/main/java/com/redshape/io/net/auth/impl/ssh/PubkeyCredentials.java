package com.redshape.io.net.auth.impl.ssh;

import com.redshape.io.interactors.ServiceID;
import com.redshape.io.net.auth.ICredentials;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Nov 8, 2010
 * Time: 12:55:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class PubkeyCredentials implements ICredentials {
    private String username;

    public PubkeyCredentials( String user ) {
        this.username = user;
    }

	@Override
    public ServiceID getServiceID() {
        return ServiceID.SSH;
    }

	@Override
    public String getUsername() {
        return this.username;
    }

}
