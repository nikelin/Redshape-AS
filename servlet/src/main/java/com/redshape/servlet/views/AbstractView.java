package com.redshape.servlet.views;

import com.redshape.i18n.impl.StandardI18NFacade;
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
    private Map<String, Object> parameters = new HashMap<String, Object>();
    private String extension;
    private String redirection;
    private ILayout layout;

    public AbstractView( String basePath, String viewPath, String extension ) {
        this.viewPath = viewPath;
        this.basePath = basePath;
        this.extension = extension;
    }

    public void setLayout( ILayout layout ) {
        this.layout = layout;
    }

    @Override
    public ILayout getLayout() {
        return this.layout;
    }

    @Override
	public void setRedirection(String path) {
		this.redirection = path;
	}

	@Override
	public String getRedirection() {
		return this.redirection;
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


    @Override
    public Map<String, Object> getAttributes() {
        return this.parameters;
    }

    @SuppressWarnings("unchecked")
	public <T> T getAttribute( String name ) {
        T object = (T) this.parameters.get(name);

        ViewAttributes attributes = ViewAttributes.valueOf( name );
        if ( attributes != null && attributes.isTranslatable() ) {
            if ( object instanceof String ) {
                object = (T) StandardI18NFacade._( String.valueOf(object) );
            }
        }

        return object;
    }

    @Override
    public String getError() {
        return this.getAttribute( ViewAttributes.ERROR );
    }

    @Override
    public void setError(String message) {
        this.setAttribute( ViewAttributes.ERROR, message );
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

	@Override
	public void setAttribute(ViewAttributes name, Object value) {
		this.setAttribute( name.name() , value);
	}

	@Override
	public <T> T getAttribute(ViewAttributes name ) {
		return this.<T>getAttribute( name.name() );
	}

}
