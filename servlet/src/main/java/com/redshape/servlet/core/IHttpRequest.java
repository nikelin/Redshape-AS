package com.redshape.servlet.core;

import com.redshape.form.IUserRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/10/10
 * Time: 11:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHttpRequest extends HttpServletRequest, IUserRequest {

    public static final String FAILED_TO_PROCESS = "__requestProcessingFailed";
    public static final String CONTEXT_TYPE_SELECTED = "__contextTypeSelected";

    public boolean isMultiPart();

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

    public <T> List<T> getListParameter( String name );

	public Float getFloatParameter( String name );

    public <T> T getObjectParameter( String name ) throws IOException;

	public void setParameter( String name, Object value );

    public void setParameters( Map<String, Object> parameters );

    public boolean hasParameter( String name );

    public String getBody() throws IOException;

    public byte[] getFileContent( String name ) throws IOException;

    public IMultipartRequest getMultipart() throws IOException;

}
