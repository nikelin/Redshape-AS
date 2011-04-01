package com.redshape.servlet.dispatchers.http;

import com.redshape.servlet.core.controllers.ControllersRegistry;
import com.redshape.servlet.core.controllers.FrontController;
import com.redshape.servlet.core.controllers.IAction;
import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.dispatchers.DispatchException;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/10/10
 * Time: 11:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpDispatcher implements IHttpDispatcher {

    public void dispatch( IHttpRequest request, IHttpResponse response ) throws DispatchException {
        try {
            IAction action = ControllersRegistry.createAction( request.getController(), request.getAction() );
            action.process( request, response );

            request.getSession().setAttribute("view", action.getView() );

            request.getRequestDispatcher( FrontController.getInstance().getLayout().getViewPath() )
                   .forward( request, response );
        } catch ( Throwable e ) {
            throw new DispatchException();
        }
    }

}
