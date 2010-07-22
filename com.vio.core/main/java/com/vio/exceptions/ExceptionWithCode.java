package com.vio.exceptions;

import com.vio.render.Renderable;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 19, 2010
 * Time: 9:47:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class ExceptionWithCode extends Exception {
    private static final Logger log = Logger.getLogger( ExceptionWithCode.class );

    private ErrorCode code;

    public ExceptionWithCode() {
        this( ErrorCode.EXCEPTION_INTERNAL );
    }

    public ExceptionWithCode( ErrorCode code) {
        this.code = code;
    }

    public ErrorCode getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        try {
            String message = ErrorCodes.getMessage( this.code );

            if ( message != null ) {
                return message;
            }

            return "";
        } catch ( Throwable e ) {
            return "";
        }
    }

}
