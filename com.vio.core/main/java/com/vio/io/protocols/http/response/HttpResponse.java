package com.vio.io.protocols.http.response;

import com.vio.io.protocols.http.HttpProtocolVersion;
import com.vio.io.protocols.http.request.HttpCode;
import com.vio.io.protocols.request.RequestHeader;
import com.vio.io.protocols.response.IResponse;
import com.vio.io.protocols.response.Response;

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

    public void setBody( String body ) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }

    public void setCode( HttpCode code ) {
        this.code = code;
    }

    public HttpCode getCode() {
        return this.code;
    }

    public HttpProtocolVersion getProtocolVersion() {
        return this.version;
    }

    public void setProtocolVersion( HttpProtocolVersion version ) {
        this.version = version;
    }

    public void setParameter( String name, String value ) {
        this.parameters.put(name, value);
    }

    public void setParameters( Map<String, String> parameters ) {
        this.parameters = parameters;
    }

    public String getParameter( String name ) {
        return this.parameters.get(name);
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public void setHeaders( Collection<RequestHeader> headers ) {
        this.headers = headers;
    }

    public Collection<RequestHeader> getHeaders() {
        return this.headers;
    }

    public void addHeader( RequestHeader header ) {
        this.headers.add(header);
    }

}
