package com.redshape.servlet.core;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/10/10
 * Time: 11:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHttpRequest extends HttpServletRequest {

    public boolean isPost();

    public void setController( String name );

    public String getController();

    public void setAction( String name );

    public String getAction();

    public String getCookie( String name );

    public <T> T getObjectParameter( String name ) throws IOException;

    public void setParameters( Map<String, Object> parameters );

    public Map<String, Object> getParameters();

    public String getBody() throws IOException;

}
