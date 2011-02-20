package com.redshape.io.protocols.http.request;

import com.redshape.io.net.adapters.socket.client.ISocketAdapter;
import com.redshape.io.net.request.RequestException;
import com.redshape.io.net.request.RequestHeader;
import com.redshape.io.protocols.http.HttpProtocolVersion;
import com.redshape.io.protocols.http.hydrators.IHttpRequestHydrator;

import java.util.*;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 3:56:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequest implements IHttpRequest {
    private static final Logger log = Logger.getLogger( HttpRequest.class );

    private String uri;
    private com.redshape.io.net.adapters.socket.client.ISocketAdapter socket;
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

    @Override
    public String getAspectName() {
        return this.aspectName;
    }

    public void setAspectName( String name ) {
        this.aspectName = name;
    }

    public void setFeatureName( String name ) {
        this.featureName = name;
    }

    @Override
    public String getFeatureName() {
        return this.featureName;
    }

    @Override
    public void setParameters( Map<String, String> parameters ) {
        this.parameters = parameters;
    }

    @Override
    public Map<String, String> getParameters() {
        return this.parameters;
    }

    public void setParameter( String name, String value ) {
        this.parameters.put(name, value);
    }

    public String getParameter( String name ) {
        return this.parameters.get(name);
    }

    @Override
    public HttpMethod getMethod() {
        return this.method;
    }

    @Override
    public void setMethod( HttpMethod method ) {
        this.method = method;
    }

    @Override
    public void setHeader( String name, Object value ) {
        this.headers.put( name, new RequestHeader( name, value ) );
    }

    @Override
    public Collection<RequestHeader> getHeaders() {
        return this.headers.values();
    }

    @Override
    public RequestHeader getHeader( String name ) {
        return this.headers.get(name);
    }

    @Override
    public void setHeaders( Collection<RequestHeader> headers ) {
        for ( RequestHeader header : headers ) {
            this.headers.put( header.getName(), header );
        }
    }

    @Override
    public boolean hasHeader( String name ) {
        return this.headers.containsKey(name);
    }

    @Override
    @Deprecated
    public void setSocket( ISocketAdapter socket ) {
        this.socket = socket;
    }

    @Override
    @Deprecated
    public ISocketAdapter getSocket() {
        return this.socket;
    }

    @Override
    public void setBody( String body ) {
        this.body = body;
    }

    @Override
    public String getBody() {
        return this.body;
    }

    @Override
    public void setProtocolVersion( HttpProtocolVersion version ) {
        this.protocolVersion = version;
    }

    @Override
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

    @Override
    public boolean isPost() {
        return this.getMethod().equals( HttpMethod.POST );
    }

    static final class HttpRequestBuilder {

        synchronized public HttpRequest buildRequest( String data, IHttpRequestHydrator hydrator ) throws RequestException {
            try {
                HttpRequest result = new HttpRequest();

                hydrator.parse(data);

                result.setHeaders( hydrator.readHeaders() );
                result.setParameters( hydrator.readParams() );
                result.setProtocolVersion(HttpProtocolVersion.valueOf( hydrator.readProtocolVersion() ) );
                result.setMethod( HttpMethod.valueOf( hydrator.readMethod() ) );
                result.setBody( hydrator.readBody() );

                return result;
            } catch ( Throwable e ) {
                log.error( e.getMessage(), e );
                throw new RequestException();
            }
        }
    }
}
