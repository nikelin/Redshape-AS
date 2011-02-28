package com.redshape.io.protocols.http.response;

import com.redshape.io.protocols.http.HttpProtocolVersion;
import com.redshape.io.protocols.http.request.HttpCode;
import com.redshape.io.protocols.core.request.RequestHeader;
import com.redshape.io.protocols.core.response.Response;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 3:37:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpResponse extends Response implements IHttpResponse {
    public static final String SP = " ";
    public static final String CRLF = "\n\r";

    private HttpCode code;
    private HttpProtocolVersion version;
    private String body;
    private Collection<RequestHeader> headers = new HashSet<RequestHeader>();

    private Map<String, String> parameters = new HashMap<String, String>();

    @Override
    public void setBody( String body ) {
        this.body = body;
    }

    @Override
    public String getBody() {
        return this.body;
    }

    @Override
    public void setCode( HttpCode code ) {
        this.code = code;
    }

    @Override
    public HttpCode getCode() {
        return this.code;
    }

    @Override
    public HttpProtocolVersion getProtocolVersion() {
        return this.version;
    }

    @Override
    public void setProtocolVersion( HttpProtocolVersion version ) {
        this.version = version;
    }

    @Override
    public void setParameter( String name, String value ) {
        this.parameters.put(name, value);
    }

    @Override
    public void setParameters( Map<String, String> parameters ) {
        this.parameters = parameters;
    }

    @Override
    public String getParameter( String name ) {
        return this.parameters.get(name);
    }

    @Override
    public Map<String, String> getParameters() {
        return this.parameters;
    }

    @Override
    public void setHeaders( Collection<RequestHeader> headers ) {
        this.headers = headers;
    }

    @Override
    public Collection<RequestHeader> getHeaders() {
        return this.headers;
    }

    @Override
    public void addHeader( RequestHeader header ) {
        this.headers.add(header);
    }

}
