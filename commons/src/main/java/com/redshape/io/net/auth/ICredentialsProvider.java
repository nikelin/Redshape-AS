package com.redshape.io.net.auth;

import java.net.InetAddress;
import java.util.Collection;

/**
 * Interface for credentials providers
 * @author nikelin
 */
public interface ICredentialsProvider {

    /**
         * Add information for authentication  on given network node
         * @param node
         * @param credentials
         */
    public void addCredentials( InetAddress node, ICredentials credentials);

    /**
         * Get authentication information for given network node
         * @param node
         * @return
         */
    public Collection<ICredentials> getCredentials( InetAddress node );

    /**
         * Get authentication information for given network node and specified service ID (samba, ssh, ftp, etc. )
         * @param node
         * @param serviceId
         * @return
         */
    public Collection<ICredentials> getCredentials( InetAddress node, String serviceId );

    /**
         * Check method to prove that provider hasn't alredy been initialized
         * @return
         */
    public boolean isInitialized();

}
