package com.redshape.servlet.core.format;

import com.redshape.servlet.core.HttpRequest;
import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.SupportType;
import com.redshape.servlet.core.controllers.ProcessingException;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 7/10/12
 * Time: 5:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class MultipartFormatProcessor implements IRequestFormatProcessor {

    @Override
    public SupportType check(IHttpRequest request) throws ProcessingException {
        return request.isMultiPart() ? SupportType.MUST : SupportType.NO;
    }

    @Override
    public void process(IHttpRequest request) throws ProcessingException {
        try {
            Iterator parameterNames  = request.getMultipart().getParameterNames();
            while ( parameterNames.hasNext() ) {
                String parameterName = (String) parameterNames.next();
                request.setParameter( parameterName, request.getMultipart().getParameter(parameterName) );
            }
        } catch ( IOException e ) {
            throw new ProcessingException( e.getMessage(), e );
        }
    }
}
