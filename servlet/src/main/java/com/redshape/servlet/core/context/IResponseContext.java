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

package com.redshape.servlet.core.context;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.core.SupportType;
import com.redshape.servlet.core.controllers.ProcessingException;
import com.redshape.servlet.views.IView;

/**
 * @author nikelin
 * @date 13:52
 */
public interface IResponseContext {

    public ContextId getContextType();

    public boolean doRedirectionHandling();

    public boolean doExceptionsHandling();

    public SupportType isSupported( IHttpRequest request );

	public SupportType isSupported( IView view );

    public void proceedResponse( IView view, IHttpRequest request, IHttpResponse response )
            throws ProcessingException;

}
