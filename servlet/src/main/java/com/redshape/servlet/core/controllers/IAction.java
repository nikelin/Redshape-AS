package com.redshape.servlet.core.controllers;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.views.IView;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:14 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IAction {

    public IView getView();

    public void process( IHttpRequest request, IHttpResponse response ) throws ProcessingException;

}
