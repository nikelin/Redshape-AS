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

package com.redshape.servlet.core.controllers;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.core.controllers.registry.IControllersRegistry;
import com.redshape.servlet.views.IView;
import com.redshape.servlet.views.IViewsFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:14 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IAction {

    public void checkPermissions() throws ProcessingException;

	public void setViewsFactory( IViewsFactory factory );
	
	public void setRegistry( IControllersRegistry registry );

    public void setView( IView view );

    public void setServletConfig(ServletConfig context);

    public ServletConfig getServletConfig();

    public IHttpRequest getRequest();

    public void setRequest( IHttpRequest request );

    public IHttpResponse getResponse();

    public void setResponse( IHttpResponse response );

    public void process() throws ProcessingException;

}
