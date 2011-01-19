package com.redshape.server;

import com.redshape.exceptions.ErrorCode;
import com.redshape.exceptions.ExceptionWithCode;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 18, 2010
 * Time: 12:34:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerException extends ExceptionWithCode {

    public ServerException() {
        super(ErrorCode.EXCEPTION_INTERNAL );
    }

    public ServerException( ErrorCode code ) {
        super( code);
    }
    
}
