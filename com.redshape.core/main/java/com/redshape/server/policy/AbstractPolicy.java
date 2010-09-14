package com.redshape.server.policy;

import com.redshape.exceptions.ExceptionWithCode;
import com.redshape.io.protocols.core.request.IRequest;
import com.redshape.server.IServer;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 20, 2010
 * Time: 5:26:47 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractPolicy<T extends IRequest, V extends IServer> implements IPolicy<T, V> {
    private V context;
    private ExceptionWithCode lastException;

    public void setContext( V context ) {
        this.context = context;
    }

    public V getContext() {
        return this.context;
    }

    protected void setLastException( ExceptionWithCode e ) {
        this.lastException = e;
    }

    public ExceptionWithCode getLastException() {
        return this.lastException;
    }

    public void resetLastException() {
        this.lastException = null;
    }

}
