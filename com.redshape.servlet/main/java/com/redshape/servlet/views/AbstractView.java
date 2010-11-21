package com.redshape.servlet.views;

import com.redshape.servlet.core.Constants;
import com.redshape.servlet.views.render.IViewRenderer;
import com.redshape.servlet.views.render.RenderException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 1:07 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractView implements IView {
    private String viewPath;
    private String basePath;
    private IViewRenderer renderer;
    private Map<String, Object> parameters = new HashMap();
    private String extension;

    public AbstractView( String basePath, String viewPath, String extension ) {
        this.viewPath = viewPath;
        this.basePath = basePath;
        this.extension = extension;
    }

    public String getBasePath() {
        return this.basePath;
    }

    public void setBasePath( String path ) {
        this.basePath = path;
    }

    public String getScriptPath() {
        StringBuilder path = new StringBuilder();
        if ( this.getBasePath() != null ) {
            path.append( this.getBasePath() );
        }

        path.append(Constants.URI_PATH_SEPARATOR )
            .append( this.getViewPath() )
            .append( "." )
            .append( this.getExtension() );

        return path.toString();
    }

    public void setExtension( String value ) {
        this.extension = value;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setAttribute( String name, Object value ) {
        this.parameters.put( name, value );
    }

    public <T> T getAttribute( String name ) {
        return (T) this.parameters.get(name);
    }

    public void setRenderer( IViewRenderer renderer ) {
        this.renderer = renderer;
    }

    public IViewRenderer getRenderer() {
        return this.renderer;
    }

    public void render() throws RenderException {
        this.getRenderer().render( this );
    }

    public void setViewPath( String path ) {
        this.viewPath = path;
    }

    public String getViewPath() {
        return this.viewPath;
    }

}
