package com.vio.io.protocols.core.request;

import com.vio.exceptions.ErrorCode;

/**
 * Created by IntelliJ IDEA.
 * ApiUser: nikelin
 * Date: Jan 13, 2010
 * Time: 1:07:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestProcessingException extends RequestException {

    public RequestProcessingException() {
        super( ErrorCode.EXCEPTION_INTERNAL );
    }

    public RequestProcessingException( ErrorCode code ) {
        super( code );
    }
}