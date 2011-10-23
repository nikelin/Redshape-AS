package com.redshape.io;


import com.redshape.io.interactors.ServiceID;

import java.util.Collection;

/**
 * @author nikelin
 */
public interface IInteractorsFactory {

    public Collection< Class<? extends INetworkConnection> > getInteractors();

    public Collection<INetworkConnection> findInteractors( INetworkNode node )
    	throws InstantiationException;

    public INetworkConnection findInteractor( ServiceID serviceID, INetworkNode node )
    	throws InstantiationException;

    public void initialize() throws InstantiationException;
    
}
