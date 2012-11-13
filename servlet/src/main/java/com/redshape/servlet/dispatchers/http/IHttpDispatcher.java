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

package com.redshape.servlet.dispatchers.http;

import com.redshape.servlet.actions.exceptions.handling.IPageExceptionHandler;
import org.springframework.context.ApplicationContextAware;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.dispatchers.DispatchException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;


/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/10/10
 * Time: 11:14 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHttpDispatcher extends ApplicationContextAware {

	public IPageExceptionHandler getExceptionHandler();

    public void dispatch( ServletConfig servletContext,
                          IHttpRequest request, IHttpResponse response ) throws DispatchException;

}