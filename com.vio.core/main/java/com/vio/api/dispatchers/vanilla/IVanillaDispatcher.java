package com.vio.api.dispatchers.vanilla;

import com.vio.api.dispatchers.IDispatcher;
import com.vio.features.IFeatureInteractor;
import com.vio.io.protocols.vanilla.request.IAPIRequest;
import com.vio.io.protocols.vanilla.response.IApiResponse;
import com.vio.server.ServerException;

public interface IVanillaDispatcher<T extends IFeatureInteractor,
                                    V extends IAPIRequest,
                                    Q extends IApiResponse>
                    extends IDispatcher<T, V, Q> {

    @Override
    public void dispatch( T requester, V invoke, Q response ) throws ServerException;
	
}