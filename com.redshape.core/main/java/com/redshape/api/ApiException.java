package com.redshape.api;

import com.redshape.exceptions.ErrorCode;
import com.redshape.exceptions.ExceptionWithCode;

public class ApiException extends ExceptionWithCode {

    public ApiException() {
        super();
    }

    public ApiException( ErrorCode code ) {
        super(code);
    }

}
