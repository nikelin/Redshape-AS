package com.redshape.io.net.auth.impl;

import com.redshape.io.interactors.ServiceID;
import com.redshape.io.net.auth.IPasswordCredentials;

/**
 * @author nikelin
 */
public class SimpleCredentials implements IPasswordCredentials {
	private ServiceID serviceID;
	private String login;
	private String password;

	public SimpleCredentials() {
		this(ServiceID.NONE, "", "");
	}

	public SimpleCredentials( ServiceID serviceID, String login, String password ) {
		this.serviceID = serviceID;
		this.login = login;
		this.password = password;
	}

	@Override
	public ServiceID getServiceID() {
		return this.serviceID;
	}

	public void setUsername( String login ) {
		this.login = login;
	}

	@Override
	public String getUsername() {
		return this.login;
	}

	public void setPassword( String password ) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

}
