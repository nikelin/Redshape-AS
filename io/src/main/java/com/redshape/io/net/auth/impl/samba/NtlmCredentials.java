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

package com.redshape.io.net.auth.impl.samba;

import com.redshape.io.interactors.ServiceID;
import com.redshape.io.net.auth.impl.SimpleCredentials;
import jcifs.smb.NtlmPasswordAuthentication;

/**
 * @author nikelin
 */
public class NtlmCredentials extends SimpleCredentials {
    private String domain;
    private String username;
    private String password;

    public NtlmCredentials( NtlmPasswordAuthentication authentication  ) {
        this( authentication.getDomain(), authentication.getUsername(),
              authentication.getPassword() );
    }

    public NtlmCredentials( String username, String password ) {
        this( null, username, password );
    }

    public NtlmCredentials( String domain, String username, String password) {
        super(ServiceID.SAMBA, username, password );
        
        this.domain = domain;
    }

    public void setDomain( String domain ) {
        this.domain = domain;
    }

    public String getDomain() {
        return this.domain;
    }

}
