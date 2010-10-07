package com.redshape.api.dispatchers.vanilla;

import com.redshape.api.InvokableEntitiesRegistry;
import com.redshape.remoting.annotations.RemoteMethod;
import com.redshape.exceptions.ErrorCode;
import com.redshape.exceptions.ExceptionWithCode;
import com.redshape.io.protocols.core.request.RequestProcessingException;
import com.redshape.io.protocols.vanilla.request.IApiRequest;
import com.redshape.io.protocols.core.request.RequestType;
import com.redshape.io.protocols.vanilla.response.IApiResponse;
import com.redshape.persistence.entities.requesters.IRequester;
import com.redshape.remoting.interfaces.RemoteInterface;
import com.redshape.server.ServerException;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 11, 2010
 * Time: 12:44:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class MethodInvocationsDispatcher implements IVanillaDispatcher<IRequester, IApiRequest, IApiResponse> {
    private static final Logger log = Logger.getLogger( MethodInvocationsDispatcher.class );

    public RequestType getDispatchingType() {
        return RequestType.METHOD_INVOKE;
    }

    public void dispatch( IRequester requester, IApiRequest request, IApiResponse response ) throws ServerException {
        if ( !InvokableEntitiesRegistry.isRegistered( request.getFeatureName() ) ) {
            response.addError( new RequestProcessingException( ErrorCode.EXCEPTION_REQUEST_INTERFACE_NOT_EXISTS ) );
            return;
        }

        if ( !InvokableEntitiesRegistry.isInvokeAllowed( request.getFeatureName(), request.getAspectName() ) ) {
            response.addError( new RequestProcessingException( ErrorCode.EXCEPTION_REQUEST_METHOD_NOT_EXISTS ) );
            return;
        }

        Class<?> invokingMethodPrototype[];
        if ( request.getParams().size() != 0 ) {
            try {
                invokingMethodPrototype = this.buildTypesList( request.getParams().values() );
            } catch ( ClassNotFoundException e ) {
                response.addError( new RequestProcessingException( ErrorCode.EXCEPTION_WRONG_REQUEST_BODY ) );
                return;
            }
        } else {
            invokingMethodPrototype = new Class[] {};            
        }

        for ( Class<?> cls : invokingMethodPrototype ) {
            log.info( cls.getCanonicalName() );
        }

        RemoteInterface hostInterface = InvokableEntitiesRegistry.getEntity( request.getFeatureName() );
        log.info("Requested interface service: " + hostInterface.getClass().getCanonicalName() );
        try {
            log.info("Real method name: " +  InvokableEntitiesRegistry.getRealMethodName( request.getFeatureName(), request.getAspectName() ) );
            Method method = hostInterface.getClass().getMethod(
                    InvokableEntitiesRegistry.getRealMethodName( request.getFeatureName(), request.getAspectName() ),
                    invokingMethodPrototype
            );
            log.info("Requested service method runner: " + method.getName() );
            if ( method.getAnnotation(RemoteMethod.class) == null ) {
                throw new RequestProcessingException( ErrorCode.EXCEPTION_REQUEST_METHOD_NOT_EXISTS );
            }

            log.info( request.getParams().values() );
            
            response.addParam("result", method.invoke( hostInterface, request.getParams().values().toArray( new Object[request.getParams().size()]) ) );
        } catch ( NoSuchMethodException e ) {
            response.addError( new RequestProcessingException( ErrorCode.EXCEPTION_REQUEST_METHOD_NOT_EXISTS ) );
        } catch ( ExceptionWithCode e ) {
            response.addError( e );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            response.addError( new RequestProcessingException() );
        }
    }

    private Class<?>[] buildTypesList( Collection<?> types ) throws ClassNotFoundException {
        Class<?>[] result = new Class[types.size()];

        int i = 0;
        for ( Object type : types ) {
            result[i++] = type.getClass();
        }

        return result;
    }


}
