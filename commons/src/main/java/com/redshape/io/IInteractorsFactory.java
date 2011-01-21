package com.redshape.io;

import java.util.Collection;

/**
 * @author nikelin
 */
public interface IInteractorsFactory {

    public Collection< Class<? extends INetworkInteractor> > getInteractors();

    public Collection<INetworkInteractor> findInteractors( INetworkNode node ) throws InstantiationException;

    public INetworkInteractor findInteractor( INetworkNode node, String serviceID ) throws InstantiationException;

    public void initialize() throws InstantiationException;

}
