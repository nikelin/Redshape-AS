package com.redshape.io.protocols.http.request;

import com.redshape.io.net.request.IRequest;
import com.redshape.io.protocols.http.HttpProtocolVersion;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 4:02:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHttpRequest extends IRequest {

    public String getAspectName();

    public String getFeatureName();

    public void setParameters( Map<String, String> parameters );

    public Map<String, String> getParameters();

    public HttpMethod getMethod();

    public void setMethod( HttpMethod method );

    public String getBody();

    public void setBody( String body );

    public void setProtocolVersion( HttpProtocolVersion version );

    public HttpProtocolVersion getProtocolVersion();

    public boolean isPost();
}
