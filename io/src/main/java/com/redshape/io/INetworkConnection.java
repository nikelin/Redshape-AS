package com.redshape.io;

import com.redshape.io.net.auth.AuthenticatorException;
import com.redshape.io.net.auth.ICredentials;
import com.redshape.io.net.auth.ICredentialsProvider;
import com.redshape.utils.config.IConfig;

import java.util.Collection;

/**
 * @author nikelin
 */
public interface INetworkConnection<ConnectionType> {

	/**
	 * Assign configuration provider to the current connection implementation
	 * @param config
	 */
	public void setConfig( IConfig config );

	/**
	 * Assign credentials provider to the current connection implementation
	 *
	 * @param provider
	 */
	public void setCredentialsProvider( ICredentialsProvider provider );

	/**
	 * Return protocol-specific connection URI
	 * to a given network node object
	 * @param node
	 * @return
	 */
	public String getConnectionUri( INetworkNode node );

	/**
	 * Create network node interactor
	 * @return
	 */
	public INetworkInteractor createInteractor();

	/**
	 * Check does that the current connection protocol supports a connections
	 * without providing security details.
	 *
	 * @return
	 */
    public boolean isAnonymousAllowed();

	/**
	 * Check status of underlying connection
	 *
	 * @return
	 * @throws NetworkInteractionException
	 */
    public boolean isConnected() throws NetworkInteractionException;

	/**
	 * Estabilish connection with remote server based on
	 * data provided by assigned ICredentialsProvider.
	 *
	 * @throws NetworkInteractionException
	 * @throws AuthenticatorException
	 */
    public void connect() throws NetworkInteractionException, AuthenticatorException;

	/**
	 * Establish connection with remote server based on a given
	 * security credentials list.
	 *
	 * @param credentials
	 * @throws NetworkInteractionException
	 */
    public void connect( Collection<ICredentials> credentials ) throws NetworkInteractionException;

	/**
	 * Close active connection
	 * @throws NetworkInteractionException
	 */
    public void close() throws NetworkInteractionException;

	/**
	 * Return raw connection represented by underlying protocol
	 * @return
	 */
    public ConnectionType getRawConnection();

}
