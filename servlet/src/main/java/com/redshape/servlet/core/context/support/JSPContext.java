package com.redshape.servlet.core.context.support;

import com.redshape.servlet.actions.exceptions.PageNotFoundException;
import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.core.context.IResponseContext;
import com.redshape.servlet.core.context.SupportType;
import com.redshape.servlet.core.controllers.FrontController;
import com.redshape.servlet.core.controllers.ProcessingException;
import com.redshape.servlet.resources.IWebResourcesHandler;
import com.redshape.servlet.views.IView;
import com.redshape.servlet.views.ViewAttributes;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.RequestDispatcher;
import java.io.FileNotFoundException;

/**
 * @author nikelin
 * @date 14:01
 */
public class JSPContext implements IResponseContext {
	public static final String EXTENSION = "jsp";

    @Autowired( required = true )
    private IWebResourcesHandler handler;

    @Autowired( required = true )
    private FrontController front;

	public FrontController getFront() {
        return front;
    }

    public void setFront(FrontController front) {
        this.front = front;
    }

    public IWebResourcesHandler getHandler() {
        return handler;
    }

    public void setHandler(IWebResourcesHandler handler) {
        this.handler = handler;
    }

    @Override
    public SupportType isSupported(IHttpRequest request) {
        return SupportType.MAY;
    }

	@Override
	public SupportType isSupported( IView view ) {
		if ( !view.getExtension().equals( EXTENSION ) ) {
			return SupportType.NO;
		}

		return SupportType.MAY;
	}

    @Override
    public void proceedResponse(IView view, IHttpRequest request, IHttpResponse response)
            throws ProcessingException {
        view.setAttribute(ViewAttributes.Env.Controller, request.getController());
        view.setAttribute(ViewAttributes.Env.Action, request.getAction() );
        view.setAttribute(ViewAttributes.Env.ResourcesHandler, this.getHandler() );

		RequestDispatcher dispatcher;
		if ( !request.getParameter("_servletContextDisableLayout").equals("Enable") ) {
        	dispatcher = request.getRequestDispatcher(
				 this.getFront().getLayout().getScriptPath() );
		} else {
			dispatcher = request.getRequestDispatcher( view.getScriptPath() );
		}

        try {
            dispatcher.forward( request, response);
        } catch ( FileNotFoundException e ) {
            throw new PageNotFoundException( e.getMessage(), e );
        } catch ( Throwable e ) {
            throw new ProcessingException( e.getMessage(), e );
        }
    }
}
