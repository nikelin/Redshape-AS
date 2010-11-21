package com.redshape.servlet.core.controllers;

import com.redshape.servlet.core.Constants;
import com.redshape.servlet.views.IView;
import com.redshape.servlet.views.ViewsFactory;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:57 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractAction implements IAction {
    private ThreadLocal<IView> view = new ThreadLocal();

    synchronized public IView getView() {
        if ( this.view.get() == null ) {
            this.view.set( this.createViewObject() );
        }

        return this.view.get();
    }

    protected String getViewPath() {
        Action actionMeta = this.getClass().getAnnotation( Action.class );

        String viewPath = actionMeta.view();
        if ( viewPath == null || viewPath.isEmpty() ) {
           viewPath = actionMeta.controller().concat( Constants.URI_PATH_SEPARATOR ).concat( actionMeta.name() );
        }

        return viewPath;
    }

    protected IView createViewObject() {
        return ViewsFactory.getDefault().createView( this.getViewPath() );
    }

}
