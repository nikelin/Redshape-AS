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

package com.redshape.servlet.actions.exceptions.handling;

import com.redshape.servlet.actions.exceptions.PageAccessDeniedException;
import com.redshape.servlet.actions.exceptions.PageAuthRequiredException;
import com.redshape.servlet.actions.exceptions.PageNotFoundException;
import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.core.controllers.ProcessingException;

import java.io.IOException;

/**
 * Interface for processing related exceptions raised in a
 * controllers context.
 *
 * @package com.redshape.servlet.actions.exceptions
 * @user cyril
 * @date 6/20/11 10:43 PM
 */
public interface IPageExceptionHandler {

    public void handleException( PageNotFoundException e, IHttpRequest request,
                                 IHttpResponse response )
		throws IOException;

    public void handleException( PageAuthRequiredException e, IHttpRequest request,
                                 IHttpResponse response )
		throws IOException ;

    public void handleException( PageAccessDeniedException e, IHttpRequest request,
                                 IHttpResponse response )
		throws IOException ;

    public void handleException( ProcessingException e, IHttpRequest request,
                                 IHttpResponse response )
		throws IOException ;

	public String getPage500();

	public String getPage404();

	public String getPage403();

	public String getPage401();


}
