package com.redshape.servlet.core.controllers;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.core.controllers.registry.IControllersRegistry;
import com.redshape.servlet.views.IView;
import com.redshape.servlet.views.IViewsFactory;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:14 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IAction {

	public void setViewsFactory( IViewsFactory factory );
	
	public void setRegistry( IControllersRegistry registry );
	
    public IView getView();

    public void process( IHttpRequest request, IHttpResponse response ) throws ProcessingException;

}
