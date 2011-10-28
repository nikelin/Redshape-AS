package com.redshape.io.net.auth;

import java.net.InetAddress;
import java.util.Collection;

/**
 * Interface for credentials providers
 * @author nikelin
 */
public interface ICredentialsProvider {

	/**
	 * Get authentication information for given network node
	 * @param node
	 * @return
	 */
	public Collection<ICredentials> getCredentials( InetAddress node ) throws AuthenticatorException;

}
