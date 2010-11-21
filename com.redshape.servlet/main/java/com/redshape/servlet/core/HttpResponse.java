package com.redshape.servlet.core;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpResponse extends HttpServletResponseWrapper implements IHttpResponse {

    public HttpResponse( HttpServletResponse response ) {
        super( response );
    }

}
