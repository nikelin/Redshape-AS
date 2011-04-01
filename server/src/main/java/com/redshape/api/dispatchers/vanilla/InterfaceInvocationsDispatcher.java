package com.redshape.api.dispatchers.vanilla;

import com.redshape.api.ErrorCodes;
import com.redshape.features.*;
import com.redshape.io.protocols.dispatchers.DispatcherException;
import com.redshape.io.protocols.vanilla.request.IApiRequest;
import com.redshape.io.protocols.core.request.RequestType;
import com.redshape.io.protocols.dispatchers.IVanillaDispatcher;
import com.redshape.io.protocols.vanilla.response.IApiResponse;

import org.apache.log4j.*;

public class InterfaceInvocationsDispatcher implements IVanillaDispatcher<IFeatureInteractor, IApiRequest, IApiResponse> {
	private static final Logger log = Logger.getLogger( InterfaceInvocationsDispatcher.class );

    public RequestType getDispatchingType() {
        return RequestType.INTERFACE_INVOKE;
    }
    
    @Override
	public void dispatch( IFeatureInteractor requester, IApiRequest invoke, IApiResponse response ) throws DispatcherException {
        try {
            log.info("Calling action " + invoke.getAspectName() + " of " + invoke.getFeatureName() + " interface...");
            IFeatureAspect featureAspect = FeaturesRegistry.getDefault().getFeatureAspect( invoke.getFeatureName(), invoke.getAspectName() );
            
            if ( featureAspect == null ) {
                throw new InteractionException(ErrorCodes.EXCEPTION_REQUEST_METHOD_NOT_EXISTS);
            }

            if ( !requester.canInteract( featureAspect ) ) {
                throw new InteractionException(ErrorCodes.EXCEPTION_ACCESS_DENIED);
            }

            for ( String parameter : invoke.getParams().keySet() ) {
                featureAspect.setAttribute( parameter, invoke.getParam(parameter) );
            }

            if ( !featureAspect.isValid() ) {
                throw new InteractionException(ErrorCodes.EXCEPTION_WRONG_REQUEST_BODY);
            }

            try {
                IInteractionResult result = featureAspect.interact( requester );
                if ( result != null ) {
                    for ( String name : invoke.hasParam("waiting") ? invoke.getMap("waiting").keySet() : result.getAttributes().keySet() ) {
                        response.addParam( name, result.getAttribute(name) );
                    }
                }
            } catch ( InteractionException e ) {
                log.error( e.getMessage(), e );
                response.addError( e );
            }
        } catch ( InteractionException e ) {
            log.error( e.getMessage(), e );
            response.addError( e );
        }
	}

}
