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

	public Long getLongParameter( String name );

	public Integer getIntegerParameter( String name );

	public Boolean getBooleanParameter( String name );

	public Boolean getCheckboxParameter( String name );

	public Float getFloatParameter( String name );

    public <T> T getObjectParameter( String name ) throws IOException;

	public void setParameter( String name, Object value );

    public void setParameters( Map<String, Object> parameters );

    public boolean hasParameter( String name ) throws IOException;

    public Map<String, Object> getParameters();

    public String getBody() throws IOException;

    public byte[] getFileContent( String name ) throws IOException;

    public IMultipartRequest getMultipart() throws IOException;

}
