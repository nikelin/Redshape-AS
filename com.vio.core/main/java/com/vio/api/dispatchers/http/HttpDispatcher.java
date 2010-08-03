package com.vio.api.dispatchers.http;

import com.vio.exceptions.ErrorCode;
import com.vio.features.FeaturesRegistry;
import com.vio.features.IFeatureAspect;
import com.vio.features.IInteractionResult;
import com.vio.features.InteractionException;
import com.vio.io.protocols.http.request.IHttpRequest;
import com.vio.io.protocols.http.response.IHttpResponse;
import com.vio.io.protocols.http.routing.IHttpRouter;
import com.vio.persistence.entities.requesters.IRequester;
import com.vio.server.ServerException;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 1, 2010
 * Time: 12:50:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpDispatcher implements IHttpDispatcher<IRequester, IHttpRequest, IHttpResponse> {
    private IHttpRouter router;

    public IHttpRouter getRouter() {
        return this.router;
    }

    public void setRouter( IHttpRouter router ) {
        this.router = router;
    }

    public void dispatch( IRequester requester, IHttpRequest request, IHttpResponse response ) throws ServerException {
        try {
            if ( this.getRouter() != null ) {
                this.getRouter().route( request );
            }

            IFeatureAspect aspect = FeaturesRegistry.getDefault()
                                                    .getFeatureAspect( request.getFeatureName(), request.getAspectName() );
            if ( aspect == null ) {
                throw new ServerException(ErrorCode.EXCEPTION_REQUEST_METHOD_NOT_EXISTS);
            }


            aspect.setRequest(request);

            IInteractionResult result = aspect.interact( requester );
            if ( result.isError() ) {
                response.addError( result );
                return;
            }

            Map<String, Object> responseValues = result.getAttributes();
            for ( String key : responseValues.keySet() ) {
                response.addParam( key, responseValues.get(key) );
            }
        } catch ( InteractionException e ) {
            response.addError( e );
        }
    }

}
