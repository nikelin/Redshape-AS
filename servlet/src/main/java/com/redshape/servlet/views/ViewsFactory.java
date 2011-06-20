package com.redshape.servlet.views;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.controllers.FrontController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

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

    @Autowired( required = true )
    private FrontController front;

    private Map<Object, IView> registry = new HashMap<Object, IView>();

    public FrontController getFront() {
        return front;
    }

    public void setFront(FrontController controller) {
        this.front = controller;
    }

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

    protected Object getSessionId( IHttpRequest request ) {
        Object id = request.getCookie("session_id");
        if ( id == null ) {
            id = request.getSession().getId();
        }

        return id;
    }

    protected IView getOrCreateView( IHttpRequest request ) {
        Object sessionId = this.getSessionId(request);
        IView view = this.registry.get(sessionId);
        if ( view == null ) {
            this.registry.put( sessionId, view = this.createViewObject() );
        }

        return view;
    }

    protected IView createViewObject() {
        return new View( this.basePath, this.extension );
    }

    @Override
    public IView getView( IHttpRequest request ) {
        IView view = this.getOrCreateView( request );
        view.setLayout( this.getFront().getLayout() );
        return view;
    }

}
