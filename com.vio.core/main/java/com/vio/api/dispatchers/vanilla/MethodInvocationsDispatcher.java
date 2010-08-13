package com.vio.api.dispatchers.vanilla;

import com.vio.api.InvokableEntitiesRegistry;
import com.vio.remoting.annotations.RemoteMethod;
import com.vio.exceptions.ErrorCode;
import com.vio.exceptions.ExceptionWithCode;
import com.vio.io.protocols.core.request.RequestProcessingException;
import com.vio.io.protocols.vanilla.request.IApiRequest;
import com.vio.io.protocols.core.request.RequestType;
import com.vio.io.protocols.vanilla.response.IApiResponse;
import com.vio.persistence.entities.requesters.IRequester;
import com.vio.server.ServerException;

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
        try {
            invokingMethodPrototype = this.buildTypesList( request.getList("types") );
        } catch ( ClassNotFoundException e ) {
            response.addError( new RequestProcessingException( ErrorCode.EXCEPTION_WRONG_REQUEST_BODY ) );
            return;
        }

        Class<?> hostEntityClass = InvokableEntitiesRegistry.getEntity( request.getFeatureName() );        
        try {
            Method method = hostEntityClass.getMethod( InvokableEntitiesRegistry.getRealMethodName( request.getFeatureName(), request.getAspectName() ), invokingMethodPrototype );
            if ( method.getAnnotation(RemoteMethod.class) == null ) {
                throw new RequestProcessingException( ErrorCode.EXCEPTION_REQUEST_METHOD_NOT_EXISTS );
            }

            response.addParam("result", method.invoke( hostEntityClass, request.getList("values") ) );
        } catch ( NoSuchMethodException e ) {
            response.addError( new RequestProcessingException( ErrorCode.EXCEPTION_REQUEST_METHOD_NOT_EXISTS ) );
        } catch ( ExceptionWithCode e ) {
            response.addError( e );
        } catch ( Throwable e ) {
            response.addError( new RequestProcessingException() );
        }
    }

    private Class<?>[] buildTypesList( Collection<?> types ) throws ClassNotFoundException {
        Class<?>[] result = new Class[types.size()];

        Iterator<?> iter = types.iterator();
        int i = 0;
        for ( Object el = iter.next(); iter.hasNext(); el = iter.next(), i++ ) {
            result[i] = Class.forName( String.valueOf( el ) );
        }

        return result;
    }


}
