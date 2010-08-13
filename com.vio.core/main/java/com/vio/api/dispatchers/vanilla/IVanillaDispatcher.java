package com.vio.api.dispatchers.vanilla;

import com.vio.api.dispatchers.IDispatcher;
import com.vio.features.IFeatureInteractor;
import com.vio.io.protocols.vanilla.request.IApiRequest;
import com.vio.io.protocols.core.request.RequestType;
import com.vio.io.protocols.vanilla.response.IApiResponse;
import com.vio.server.ServerException;

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