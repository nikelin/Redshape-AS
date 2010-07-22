package com.vio.features;

import com.vio.exceptions.ErrorCode;
import com.vio.exceptions.ExceptionWithCode;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 6, 2010
 * Time: 12:30:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class InteractionException extends ExceptionWithCode {

    public InteractionException() {
        super( ErrorCode.EXCEPTION_INTERNAL );
    }

    public InteractionException( ExceptionWithCode cause ) {
        super( cause.getCode() );
    }

    public InteractionException( ErrorCode code ) {
        super(code);
    }

}
