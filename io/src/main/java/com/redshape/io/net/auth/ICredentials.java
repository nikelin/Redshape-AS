package com.redshape.io.net.auth;

import com.redshape.io.interactors.ServiceID;

/**
 * @author nikelin
 */
public interface ICredentials {

    public ServiceID getServiceID();

    public String getUsername();

}
