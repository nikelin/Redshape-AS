package com.redshape.servlet.views;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 2:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class ViewsFactory implements IViewsFactory {
    public String basePath;
    public String extension;

    public void setBasePath( String basePath ) {
        this.basePath = basePath;
    }

    public String getBasePath() {
        return this.basePath;
    }

    public void setExtension( String extension ) {
        this.extension = extension;
    }

    public String getExtension() {
        return this.extension;
    }

    public IView createView( String viewPath ) {
        return new View( this.getBasePath(), viewPath, this.getExtension() );
    }

}
