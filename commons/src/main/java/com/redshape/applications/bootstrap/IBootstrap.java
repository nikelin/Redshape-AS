package com.redshape.applications.bootstrap;

import com.redshape.utils.TimeSpan;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 22, 2010
 * Time: 1:56:52 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IBootstrap {

    public void scheduleTask( Runnable runnable, TimeSpan delay, TimeSpan interval );

    public void addAction( IBootstrapAction action );

    public void clearActions();

    public void clear();
 
    public void removeAction( Object id );

    public void enableAction( Object id );

    public void disableAction( Object id );

    public List<IBootstrapAction> getActions();

    public IBootstrapAction getAction( Object id );

    public void init() throws BootstrapException;

}
