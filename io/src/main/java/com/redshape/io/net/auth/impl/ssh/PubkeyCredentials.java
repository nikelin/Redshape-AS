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
