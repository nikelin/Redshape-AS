package com.redshape.servlet.core.format;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.SupportType;
import com.redshape.servlet.core.controllers.ProcessingException;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 7/10/12
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IRequestFormatProcessor {

    public SupportType check( IHttpRequest request ) throws ProcessingException;

    /**
     * @param request
     * @throws ProcessingException
     */
    public void process( IHttpRequest request ) throws ProcessingException;

}
