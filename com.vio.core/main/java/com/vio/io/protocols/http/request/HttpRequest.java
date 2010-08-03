package com.vio.io.protocols.http.request;

import com.vio.io.protocols.http.HttpProtocolVersion;
import com.vio.io.protocols.http.hydrators.IHttpRequestHydrator;
import com.vio.io.protocols.core.hydrators.RequestHydrator;
import com.vio.io.protocols.core.request.IRequest;
import com.vio.io.protocols.core.request.RequestException;
import com.vio.io.protocols.core.request.RequestHeader;
import com.vio.server.adapters.socket.client.ISocketAdapter;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 3:56:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequest implements IHttpRequest {
    private String uri;
    private ISocketAdapter socket;
    private Map<String, RequestHeader> headers = new HashMap<String, RequestHeader>();
    private Map<String, String> parameters = new HashMap<String, String>();
    private HttpMethod method;
    private String body;
    private HttpProtocolVersion protocolVersion;

    private String aspectName;
    private String featureName;

    public String getUri() {
        return this.uri;
    }

    public String getAspectName() {
        return this.aspectName;
    }

    public void setAspectName( String name ) {
        this.aspectName = name;
    }

    public void setFeatureName( String name ) {
        this.featureName = name;
    }

    public String getFeatureName() {
        return this.featureName;
    }

    public void setParameters( Map<String, String> parameters ) {
        this.parameters = parameters;
    }

    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public void setParameter( String name, String value ) {
        this.parameters.put(name, value);
    }

    public String getParameter( String name ) {
        return this.parameters.get(name);
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public void setMethod( HttpMethod method ) {
        this.method = method;
    }

    public void setHeader( String name, Object value ) {
        this.headers.put( name, new RequestHeader( name, value ) );
    }

    public Collection<RequestHeader> getHeaders() {
        return this.headers.values();
    }

    public RequestHeader getHeader( String name ) {
        return this.headers.get(name);
    }

    public void setHeaders( Collection<RequestHeader> headers ) {
        for ( RequestHeader header : headers ) {
            this.headers.put( header.getName(), header );
        }
    }

    public boolean hasHeader( String name ) {
        return this.headers.containsKey(name);
    }

    public void setSocket( ISocketAdapter socket ) {
        this.socket = socket;
    }

    public ISocketAdapter getSocket() {
        return this.socket;
    }

    public void setBody( String body ) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }

    public void setProtocolVersion( HttpProtocolVersion version ) {
        this.protocolVersion = version;
    }

    public HttpProtocolVersion getProtocolVersion() {
        return this.protocolVersion;
    }

    public static HttpRequest buildRequest( String data, IHttpRequestHydrator hydrator ) throws RequestException {
        HttpRequestBuilder builder = new HttpRequestBuilder();
        HttpRequest result = builder.buildRequest(data, hydrator);
        if ( !isValid(result) ) {
            throw new RequestException();
        }

        return result;
    }

    protected static boolean isValid( HttpRequest request ) {
        if ( request.getMethod() == null ) {
           return false;
        }

        if ( request.getUri() == null ) {
            return false;
        }

        return true;
    }

    public boolean isPost() {
        return this.getMethod().equals( HttpMethod.POST );
    }

    static final class HttpRequestBuilder {

        public HttpRequest buildRequest( String data, IHttpRequestHydrator hydrator ) throws RequestException {
            try {
                HttpRequest result = new HttpRequest();

                synchronized ( hydrator ) {
                    hydrator.parse(data);
                }

                result.setHeaders( hydrator.readHeaders() );
                result.setParameters( hydrator.readParams() );
                result.setProtocolVersion(HttpProtocolVersion.valueOf( hydrator.readProtocolVersion() ) );
                result.setMethod( HttpMethod.valueOf( hydrator.readMethod() ) );
                result.setBody( hydrator.readBody() );

                return result;
            } catch ( Throwable e ) {
                throw new RequestException();
            }
        }
    }
}
