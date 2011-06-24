package com.redshape.servlet.views;

import com.redshape.servlet.core.controllers.ProcessingException;
import com.redshape.servlet.views.render.IViewRenderer;
import com.redshape.servlet.views.render.RenderException;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:32 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IView {

    public void setException( ProcessingException e );

    public ProcessingException getException();

    public String getError();

    public void setError( String message );

	public void setRedirection( String path );
	
	public String getRedirection();
	
    public String getExtension();

    public void setExtension( String value );

    public void setAttribute( ViewAttributes name, Object value );
    
    public void setAttribute( String name, Object value );

    public <T> T getAttribute( ViewAttributes name );
    
    public <T> T getAttribute( String name );

    public Map<String, Object> getAttributes();

    public void setBasePath( String path );

    public String getBasePath();

    public String getScriptPath();

    public void setViewPath( String path );

    public String getViewPath();

    public void render() throws RenderException;

    public void setRenderer( IViewRenderer engine );

    public IViewRenderer getRenderer();

    public ILayout getLayout();

    public void setLayout( ILayout layout );

    public void reset( ResetMode mode );

}
