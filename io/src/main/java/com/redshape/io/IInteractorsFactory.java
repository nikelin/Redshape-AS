package com.redshape.io;


import com.redshape.io.interactors.ServiceID;

import java.util.Collection;

/**
 * @author nikelin
 */
public interface IInteractorsFactory {

	/**
	 * Return registered within current factory interaction objects
	 * @return
	 */
	public Collection<Class<? extends INetworkConnection>> getInteractors();

	/**
	 * Find all interactors which conform with a given remote network node
	 * @param node
	 * @return
	 * @throws InstantiationException
	 */
	public Collection<INetworkConnection> findInteractors( INetworkNode node )
			throws InstantiationException;

	/**
	 * Find network interactor for a given protocol ID and remote network node
	 *
	 * @param serviceID
	 * @param node
	 * @return
	 * @throws InstantiationException
	 */
	public INetworkConnection findInteractor( ServiceID serviceID, INetworkNode node )
			throws InstantiationException;

}
