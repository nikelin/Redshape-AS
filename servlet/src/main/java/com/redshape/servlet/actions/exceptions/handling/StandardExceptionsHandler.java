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
import com.redshape.servlet.views.ViewHelper;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @package com.redshape.servlet.actions.exceptions.handling
 * @user cyril
 * @date 6/20/11 10:57 PM
 */
public class StandardExceptionsHandler extends AbstractPageExceptionHandler {
    private static final Logger log = Logger.getLogger(StandardExceptionsHandler.class);

    private String page404 = "/errors/404.html";
    private String page403 = "/errors/403.html";
    private String page401 = "/errors/401.html";
    private String page500 = "/errors/500.html";

    public StandardExceptionsHandler() {
        super();
    }

    public String getPage500() {
        return page500;
    }

    public void setPage500(String page500) {
        this.page500 = page500;
    }

    public String getPage404() {
        return page404;
    }

    public void setPage404(String page404) {
        this.page404 = page404;
    }

    public String getPage403() {
        return page403;
    }

    public void setPage403(String page403) {
        this.page403 = page403;
    }

    public String getPage401() {
        return page401;
    }

    public void setPage401(String page401) {
        this.page401 = page401;
    }

    @Override
    public void handleException(PageNotFoundException e,
					IHttpRequest request, IHttpResponse response)
		throws IOException {
        try {
            log.info("Error message (404):");
            log.info( e.getMessage(), e );

            this.sendRedirect( request, response, ViewHelper.url( this.getPage404() ) );
        } catch ( ServletException ex ) {
            log.error( ex.getMessage(), ex );
        }
    }

    @Override
    public void handleException(PageAuthRequiredException e,
						IHttpRequest request, IHttpResponse response)
		throws IOException  {
        try {
            log.info("Error message (401):");
            log.info( e.getMessage(), e );

            this.sendRedirect( request, response, ViewHelper.url( this.getPage401() ) );
        } catch ( ServletException ex ) {
            log.error( ex.getMessage(), ex );
        }
    }

    @Override
    public void handleException(PageAccessDeniedException e,
						IHttpRequest request, IHttpResponse response)
		throws IOException  {
        try {
            log.info("Error message (403):");
            log.info( e.getMessage(), e );

            this.sendRedirect( request, response, ViewHelper.url( this.getPage403() ) );
        } catch ( ServletException ex ) {
            log.error( ex.getMessage(), ex );
        }
    }

    @Override
    protected void unknownExceptionHandler(ProcessingException e,
								   IHttpRequest request, IHttpResponse response)
		throws IOException  {
        try {
            log.error("Error message (500):");
            log.error( e.getMessage(), e );

            this.sendRedirect( request, response, ViewHelper.url( this.getPage500() ) );
        } catch ( ServletException ex ) {
            log.error( ex.getMessage(), ex );
        }
    }

	protected void sendRedirect( IHttpRequest request, IHttpResponse response, String path )
		throws ServletException, IOException {
		if ( !response.isCommitted() ) {
			response.sendRedirect(path);
		} else {
			// Impossible to send redirection headers
		}
	}
}
