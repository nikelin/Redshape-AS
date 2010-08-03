package com.vio.io.protocols.http.response;

import com.vio.io.protocols.http.HttpProtocolVersion;
import com.vio.io.protocols.http.request.HttpCode;
import com.vio.io.protocols.http.request.HttpMethod;
import com.vio.io.protocols.response.IResponse;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 3:39:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHttpResponse extends IResponse {

    public void setBody( String body );

    public String getBody();

    public void setCode( HttpCode code );

    public HttpCode getCode();

    public void setProtocolVersion( HttpProtocolVersion version );

    public HttpProtocolVersion getProtocolVersion();

    public void setParameter( String name, String value );

    public String getParameter( String name );

    public Map<String, String> getParameters();

    public void setParameters( Map<String, String> parameters );

}
