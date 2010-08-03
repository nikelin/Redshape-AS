package com.vio.api;

import com.vio.exceptions.ErrorCode;
import com.vio.exceptions.ExceptionWithCode;

public class ApiException extends ExceptionWithCode {

    public ApiException() {
        super();
    }

    public ApiException( ErrorCode code ) {
        super(code);
    }

}
