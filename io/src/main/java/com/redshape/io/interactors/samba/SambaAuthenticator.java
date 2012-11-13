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

package com.redshape.io.interactors.samba;

import jcifs.smb.NtlmAuthenticator;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbAuthException;

import com.redshape.io.net.auth.impl.samba.NtlmCredentials;


/**
 * @author nikelin
 */
public class SambaAuthenticator extends NtlmAuthenticator {
    private NtlmPasswordAuthentication authentication;

    public SambaAuthenticator() {
        NtlmAuthenticator.setDefault(this);
    }

    public boolean isAuthException() {
        return this.getRequestingException() != null;
    }

    public SmbAuthException getAuthException() {
        return this.getRequestingException();
    }

    public NtlmPasswordAuthentication createNtlmPasswordAuthentication(NtlmCredentials credentials ) {
        return new NtlmPasswordAuthentication(
            credentials.getDomain(),
            credentials.getUsername(),
            credentials.getPassword()
        );
    }

}
