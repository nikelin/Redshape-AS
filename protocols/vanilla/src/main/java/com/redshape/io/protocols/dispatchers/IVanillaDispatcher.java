package com.redshape.io.protocols.dispatchers;

import com.redshape.api.requesters.IRequester;
import com.redshape.io.protocols.vanilla.request.IApiRequest;
import com.redshape.io.protocols.core.request.RequestType;
import com.redshape.io.protocols.vanilla.response.IApiResponse;

public interface IVanillaDispatcher<T extends IRequester,
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
    public void dispatch( T requester, V invoke, Q response ) throws DispatcherException;
	
}