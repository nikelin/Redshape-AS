package com.vio.api;

import com.vio.exceptions.ErrorCode;
import com.vio.exceptions.ExceptionWithCode;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 10, 2010
 * Time: 3:54:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class ApiException extends ExceptionWithCode {

    public ApiException() {
        super();
    }

    public ApiException( ErrorCode code ) {
        super(code);
    }

}
