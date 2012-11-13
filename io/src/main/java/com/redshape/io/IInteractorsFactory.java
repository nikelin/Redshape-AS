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
