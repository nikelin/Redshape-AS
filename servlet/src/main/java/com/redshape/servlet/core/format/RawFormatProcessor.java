package com.redshape.servlet.core.format;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.SupportType;
import com.redshape.servlet.core.controllers.ProcessingException;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 10/16/12
 * Time: 4:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class RawFormatProcessor implements IRequestFormatProcessor {
    private static final Logger log = Logger.getLogger(RawFormatProcessor.class);

    public static final String MARKER_CONTENT_TYPE = "application/octet-stream";

    @Override
    public SupportType check(IHttpRequest request) throws ProcessingException {
        if ( !request.isPost() || request.getContentType() == null ) {
            return SupportType.NO;
        }

        if ( request.getContentType().equals(MARKER_CONTENT_TYPE)) {
            request.setAttribute(ComposedProcessor.SKIP_ADDITIONAL_PROCESSING, true );
            return SupportType.MUST;
        }

        return SupportType.NO;
    }

    @Override
    public void process(IHttpRequest request) throws ProcessingException {
        log.info("Skipping binary request body processing...");
    }
}
