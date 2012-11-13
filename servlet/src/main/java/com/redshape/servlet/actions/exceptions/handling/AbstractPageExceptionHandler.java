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

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.core.controllers.ProcessingException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @package com.redshape.servlet.actions.exceptions.handling
 * @user cyril
 * @date 6/20/11 10:49 PM
 */
public abstract class AbstractPageExceptionHandler implements IPageExceptionHandler {
    private Map<Class<? extends ProcessingException>, Method> interceptors
                    = new HashMap< Class<? extends ProcessingException>, Method>();
    private boolean initialized;
    protected static String HANDLER_NAME = "handleException";

    public AbstractPageExceptionHandler() {
        super();

        this.init();
    }

    protected void init() {
        if ( this.initialized ) {
            return;
        }

        for ( Method method : this.getClass().getDeclaredMethods() ) {
            if ( this.isHandlerMethod(method) ) {
                this.interceptors.put(
                        method.getParameterTypes()[0].asSubclass(ProcessingException.class),
                        method );
            }
        }

        this.initialized = true;
    }

    protected boolean isHandlerMethod( Method method ) {
        Class<?>[] params = method.getParameterTypes();
        if ( params.length < 3 ) {
            return false;
        }

        return method.getName().equals( HANDLER_NAME )
                        && params[0] != ProcessingException.class
                            && ProcessingException.class.isAssignableFrom( params[0] );
    }

    protected abstract void unknownExceptionHandler( ProcessingException e,
                                                     IHttpRequest request, IHttpResponse response )

		throws IOException ;

    @Override
    public void handleException(ProcessingException e, IHttpRequest request, IHttpResponse response)

		throws IOException {
        Method method = this.interceptors.get( e.getClass() );
        if ( method == null ) {
            this.unknownExceptionHandler(e, request, response);
        }

        try {
            method.invoke( this, e, request, response );
        } catch ( InvocationTargetException ex ) {
            throw new IllegalStateException( e.getCause().getMessage(), e.getCause() );
        } catch ( Throwable ex ) {
            throw new IllegalStateException( e.getMessage(), e );
        }
    }
}
