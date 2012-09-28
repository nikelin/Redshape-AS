package com.redshape.servlet.dispatchers.http;

import com.redshape.servlet.actions.exceptions.handling.IPageExceptionHandler;
import org.springframework.context.ApplicationContextAware;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.dispatchers.DispatchException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;


/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/10/10
 * Time: 11:14 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHttpDispatcher extends ApplicationContextAware {

	public IPageExceptionHandler getExceptionHandler();

    public void dispatch( ServletConfig servletContext,
                          IHttpRequest request, IHttpResponse response ) throws DispatchException;

}