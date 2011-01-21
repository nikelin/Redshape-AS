package com.redshape.ui.data.loaders;

import com.redshape.io.protocols.http.request.HttpMethod;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 23:18
 * To change this template use File | Settings | File Templates.
 */
public class HttpConfig implements ILoaderConfig {
    private URL url;
    private int timeout;
    private HttpMethod method;
    private Map<String, Object> parameters = new HashMap<String, Object>();

    public HttpConfig( HttpMethod method, URL url ) {
        this.url = url;
        this.method = method;
    }

    public URL getURL() {
        return this.url;
    }

    public void setTimeout( int timeout ) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public Map<String, Object> getParameters() {
        return this.parameters;
    }

    public void setParameter( String name, Object value ) {
        this.parameters.put(name, value);
    }

    public <V> V getParameter( String name ) {
        return (V) this.parameters.get(name);
    }

    public void setMethod( HttpMethod method ) {
        this.method = method;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

}
