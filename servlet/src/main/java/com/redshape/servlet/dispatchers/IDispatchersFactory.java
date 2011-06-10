package com.redshape.servlet.dispatchers;

import com.redshape.servlet.dispatchers.http.IHttpDispatcher;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/10/10
 * Time: 11:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IDispatchersFactory {

    public IHttpDispatcher getDispatcher( Class<? extends IHttpDispatcher> clazz ) throws InstantiationException;

}
