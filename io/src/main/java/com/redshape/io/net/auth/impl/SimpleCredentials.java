/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
