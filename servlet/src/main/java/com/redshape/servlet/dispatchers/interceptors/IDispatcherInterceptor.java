/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
