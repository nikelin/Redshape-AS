package com.redshape.servlet.views;

import com.redshape.servlet.core.controllers.IAction;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 1:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class Layout extends AbstractView implements ILayout {
    private IView content;
    
    private IAction dispatchAction;

    public Layout( String basePath, String viewPath, String extension ) {
        this(null, basePath, viewPath, extension);
    }

    public Layout( IAction dispatchAction, String basePath, String viewPath, String extension ) {
        super(basePath, viewPath, extension);
        
        this.dispatchAction = dispatchAction;
    }

    @Override
    public IAction getDispatchAction() {
        return this.dispatchAction;
    }

    public void setContent( IView content ) {
        this.content = content;
    }

    public IView getContent() {
        return this.content;
    }

    @Override
    public void setLayout( ILayout layout ) {
        throw new UnsupportedOperationException("Operation not supported on ILayout type");
    }

    @Override
    public ILayout getLayout() {
        throw new UnsupportedOperationException("Operation not supported on ILayout type");
    }

}
