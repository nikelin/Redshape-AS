package com.vio.api.dispatchers.impl;

import java.util.List;
import java.util.Map;
import java.util.Observable;

import com.vio.api.ApiException;
import com.vio.api.dispatchers.Dispatcher;
import com.vio.features.*;
import com.vio.io.protocols.vanilla.request.InterfaceInvocation;
import com.vio.io.protocols.response.IResponse;
import com.vio.exceptions.ErrorCode;
import com.vio.persistence.entities.requesters.IRequester;
import com.vio.server.ServerException;

import org.apache.log4j.*;

public class DispatcherImpl extends Observable implements Dispatcher {
	private static final Logger log = Logger.getLogger( DispatcherImpl.class );
	
	public void dispatch( IRequester requester, InterfaceInvocation invoke, IResponse response ) throws ServerException {
        try {
            log.info("Calling action " + invoke.getAction() + " of " + invoke.getInterfaceId() + " interface...");
            IFeatureAspect featureAspect = FeaturesRegistry.getDefault().getFeatureAspect( invoke.getInterfaceId(), invoke.getAction() );
            if ( featureAspect == null ) {
                throw new ApiException( ErrorCode.EXCEPTION_REQUEST_METHOD_NOT_EXISTS );
            }

            /**
             * Check for interactor ability to request current feature aspect
             *
             * @FIXME: пока что отключил
             */
            if ( false && !requester.canInteract( featureAspect ) ) {
                throw new ApiException( ErrorCode.EXCEPTION_ACCESS_DENIED );
            }

            for ( String parameter : invoke.getParams().keySet() ) {
                featureAspect.setAttribute( parameter, invoke.getParam(parameter) );
            }

            if ( !featureAspect.isValid() ) {
                throw new ApiException( ErrorCode.EXCEPTION_ACCESS_DENIED );
            }

            try {
                IInteractionResult result = featureAspect.interact( requester );
                if ( result != null ) {
                    for ( String name : invoke.hasParam("waiting") ? invoke.getMap("waiting").keySet() : result.getAttributes().keySet() ) {
                        response.addParam( name, result.getAttribute(name) );
                    }
                }
            } catch ( InteractionException e ) {
                response.addError( e );
            }
        } catch ( ApiException e ) {
            log.error( e.getMessage(), e );
            response.addError( e );
        }
	}

}
