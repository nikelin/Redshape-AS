package com.redshape.servlet.core.context;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.core.controllers.ProcessingException;
import com.redshape.servlet.views.IView;

/**
 * @author nikelin
 * @date 13:52
 */
public interface IResponseContext {

    public boolean doExceptionsHandling();

    public SupportType isSupported( IHttpRequest request );

	public SupportType isSupported( IView view );

    public void proceedResponse( IView view, IHttpRequest request, IHttpResponse response )
            throws ProcessingException;

}
