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
