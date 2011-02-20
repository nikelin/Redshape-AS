package com.redshape.io.server.policy;

import com.redshape.io.server.IServer;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 14, 2010
 * Time: 1:46:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPolicy<T extends Object> {

    public void setContext( IServer<?, T> server );

    public IServer<?, T> getContext();

    public ApplicationResult applicate( T subject );

}
