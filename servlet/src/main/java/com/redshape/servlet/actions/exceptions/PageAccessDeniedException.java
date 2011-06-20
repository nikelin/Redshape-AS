package com.redshape.servlet.actions.exceptions;

import com.redshape.servlet.core.controllers.ProcessingException;

/**
 * @package com.redshape.servlet.actions.exceptions
 * @user cyril
 * @date 6/20/11 10:42 PM
 */
public class PageAccessDeniedException extends ProcessingException {

    public PageAccessDeniedException() {
        super();
    }

    public PageAccessDeniedException(String message) {
        super(message);
    }

    public PageAccessDeniedException(String message, Throwable e) {
        super(message, e);
    }
}
