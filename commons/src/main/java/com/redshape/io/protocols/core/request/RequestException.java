package com.redshape.io.protocols.core.request;

import com.redshape.exceptions.ErrorCode;
import com.redshape.exceptions.ExceptionWithCode;

/**
 * Created by IntelliJ IDEA.
 * ApiUser: nikelin
 * Date: Jan 13, 2010
 * Time: 1:07:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestException extends ExceptionWithCode {

    public RequestException() {
        super( ErrorCode.EXCEPTION_INTERNAL);
    }

    public RequestException( ErrorCode code ) {
        super( code);
    }
}
