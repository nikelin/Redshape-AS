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
