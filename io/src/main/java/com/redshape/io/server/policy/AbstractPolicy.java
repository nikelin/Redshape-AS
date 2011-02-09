package com.redshape.io.server.policy;

import com.redshape.io.net.request.IRequest;
import com.redshape.io.server.IServer;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 20, 2010
 * Time: 5:26:47 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractPolicy<T extends IRequest, V extends IServer> implements IPolicy<T, V> {
    private V context;

    public void setContext( V context ) {
        this.context = context;
    }

    public V getContext() {
        return this.context;
    }

}
