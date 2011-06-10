package com.redshape.servlet.core;

import javax.servlet.http.HttpServletRequest;
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

    public void setParameters( Map<String, Object> parameters );

    public Map<String, Object> getParameters();

}
