package com.redshape.io.protocols.dispatchers;

import com.redshape.io.net.fetch.IResponse;
import com.redshape.io.protocols.core.request.IRequest;


/**
 * IEntity responsible on dispatching incoming requests
 * to one of the available features (@see com.redshape.features )
 *
 * Process given request and write results in a view of items
 * for Map<V,Q>.
 *
 * @author nikelin
 * @group api
 */
public interface IDispatcher<T, V extends IRequest, Q extends IResponse> {
    
    public void dispatch( T requester, V request, Q response ) throws DispatcherException;

}
