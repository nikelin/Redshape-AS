package com.redshape.servlet.dispatchers.interceptors;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.core.context.IResponseContext;
import com.redshape.servlet.core.controllers.ProcessingException;
import com.redshape.servlet.views.IView;

/**
 * Dispatcher-time interceptor which able to affect on
 * request dispatching logic e.g. access policy, session restoration mechanisms, etc.
 *
 * @package com.redshape.servlet.dispatchers.interceptors
 * @user cyril
 * @date 7/17/11 7:12 PM
 */
public interface IDispatcherInterceptor {

    /**
     * Invokes after view constructed but before actual requested action
     * processing started.
     *
     * @param view
     * @param request
     * @param response
     * @throws ProcessingException
     */
    public void invoke( IResponseContext context, IView view,
                        IHttpRequest request, IHttpResponse response )
            throws ProcessingException;

}
