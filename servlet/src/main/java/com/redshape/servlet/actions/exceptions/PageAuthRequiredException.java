package com.redshape.servlet.actions.exceptions;

import com.redshape.servlet.core.controllers.ProcessingException;

/**
 * @package com.redshape.servlet.actions.exceptions
 * @user cyril
 * @date 6/20/11 10:42 PM
 */
public class PageAuthRequiredException extends ProcessingException {

    public PageAuthRequiredException() {
        super();
    }

    public PageAuthRequiredException(String message) {
        super(message);
    }

    public PageAuthRequiredException(String message, Throwable e) {
        super(message, e);
    }
}
