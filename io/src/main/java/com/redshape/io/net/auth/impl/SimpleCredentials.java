package com.redshape.io.net.auth.impl;

import com.redshape.io.net.auth.IPasswordCredentials;


/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 11/3/10
 * Time: 2:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleCredentials implements IPasswordCredentials {
    private String serviceID;
    private String login;
    private String password;

    public SimpleCredentials() {
        this("", "", "");
    }

    public SimpleCredentials( String serviceID, String login, String password ) {
        this.serviceID = serviceID;
        this.login = login;
        this.password = password;
    }

    public String getServiceID() {
        return this.serviceID;
    }

    public void setUsername( String login ) {
        this.login = login;
    }

    public String getUsername() {
        return this.login;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

}
