package com.redshape.servlet.views;

import com.redshape.servlet.core.IHttpRequest;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 2:14 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IViewsFactory {

    public void setBasePath( String basePath );

    public String getBasePath();

    public void setExtension( String extension );

    public String getExtension();

    public IView getView( IHttpRequest request );

}
