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

package com.redshape.io;

import com.redshape.io.net.auth.ICredentialsProvider;
import com.redshape.utils.config.IConfig;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 11/3/10
 * Time: 5:07 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractNetworkConnection<T>
                implements INetworkConnection<T> {
    private INetworkNode node;
    private String protocolId;
    private boolean isAnonymousAllowed;
    private ICredentialsProvider credentialsProvider;
    private IConfig config;

    public AbstractNetworkConnection(String protocolId, INetworkNode node) {
        this.protocolId = protocolId;
        this.node = node;
    }

	protected INetworkNode getNode() {
		return this.node;
	}

    public void setConfig( IConfig config ) {
        this.config = config;
    }

    public IConfig getConfig() {
        return this.config;
    }

    public void markAnonymousAllowed( boolean value ) {
        this.isAnonymousAllowed = value;
    }

    public boolean isAnonymousAllowed() {
        return this.isAnonymousAllowed;
    }

    public void setCredentialsProvider( ICredentialsProvider provider ) {
        this.credentialsProvider = provider;
    }

    public ICredentialsProvider getCredentialsProvider() {
        return this.credentialsProvider;
    }

}
