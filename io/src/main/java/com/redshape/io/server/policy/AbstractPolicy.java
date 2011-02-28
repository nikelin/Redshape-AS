package com.redshape.io.server.policy;

import com.redshape.io.server.IServer;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 20, 2010
 * Time: 5:26:47 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractPolicy<T> implements IPolicy<T> {
    private IServer<?, T> context;

    @Override
    public void setContext( IServer<?, T> context ) {
        this.context = context;
    }

    public IServer<?, T> getContext() {
        return this.context;
    }

}
