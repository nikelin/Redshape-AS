package com.redshape.servlet.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/10/10
 * Time: 11:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequest extends HttpServletRequestWrapper implements IHttpRequest {

    private String controller;
    private String action;
    private Map<String, Object> parameters = new HashMap();

    public HttpRequest( HttpServletRequest request ) {
        super(request);
    }

    public boolean isPost() {
        return this.getMethod().equals("POST");
    }

    public void setController( String name ) {
        this.controller = name;
    }

    public String getController() {
        return this.controller;
    }

    public void setAction( String name ) {
        this.action = name;
    }

    public String getAction() {
        return this.action;
    }

    public void setParameters( Map<String, Object> parameters ) {
        this.parameters = parameters;
    }

    public Map<String, Object> getParameters() {
        return this.parameters;
    }

}
