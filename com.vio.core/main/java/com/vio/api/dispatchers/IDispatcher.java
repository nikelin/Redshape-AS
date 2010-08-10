package com.vio.api.dispatchers;

import com.vio.features.IFeatureInteractor;
import com.vio.io.protocols.core.request.IRequest;
import com.vio.io.protocols.core.response.IResponse;
import com.vio.server.ServerException;

/**
 * IEntity responsible on dispatching incoming requests
 * to one of the available features (@see com.vio.features )
 *
 * Process given request and write results in a view of items
 * for Map<V,Q>.
 *
 * @author nikelin
 * @group api
 */
public interface IDispatcher<T extends IFeatureInteractor, V extends IRequest, Q extends IResponse> {
    
    public void dispatch( T requester, V request, Q response ) throws ServerException;

}
