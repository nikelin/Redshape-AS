package com.redshape.servlet.views;

import com.redshape.servlet.views.render.IViewRenderer;
import com.redshape.servlet.views.render.RenderException;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:32 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IView {

    public String getExtension();

    public void setExtension( String value );

    public void setAttribute( String name, Object value );

    public <T> T getAttribute( String name );

    public void setBasePath( String path );

    public String getBasePath();

    public String getScriptPath();

    public void setViewPath( String path );

    public String getViewPath();

    public void render() throws RenderException;

    public void setRenderer( IViewRenderer engine );

    public IViewRenderer getRenderer();

}
