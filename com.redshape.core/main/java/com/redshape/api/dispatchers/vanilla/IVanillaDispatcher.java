package com.redshape.api.dispatchers.vanilla;

import com.redshape.api.dispatchers.IDispatcher;
import com.redshape.features.IFeatureInteractor;
import com.redshape.io.protocols.vanilla.request.IApiRequest;
import com.redshape.io.protocols.core.request.RequestType;
import com.redshape.io.protocols.vanilla.response.IApiResponse;
import com.redshape.server.ServerException;

public interface IVanillaDispatcher<T extends IFeatureInteractor,
                                    V extends IApiRequest,
                                    Q extends IApiResponse>
                    extends IDispatcher<T, V, Q> {

    /**
     * Returns type of IApiRequest which can be dispatched by current
     * dispatcher entity
     *
     * @return RequestType
     */
    public RequestType getDispatchingType();
    
    @Override
    public void dispatch( T requester, V invoke, Q response ) throws ServerException;
	
}