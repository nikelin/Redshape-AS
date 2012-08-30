package com.redshape.forker.handlers;

import com.redshape.forker.IForkCommand;
import com.redshape.forker.IForkCommandResponse;
import com.redshape.forker.ProcessException;
import com.redshape.utils.events.IEventDispatcher;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/27/12
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IForkCommandExecutor extends IEventDispatcher, Runnable {

    public void addHandler( IForkCommandHandler handler );

    public void removeHandler( IForkCommandHandler handler );

    public void clearHandlers();

    public boolean isStarted();

    public <T extends IForkCommandResponse> T execute( IForkCommand command ) throws ProcessException;

    public void respond( IForkCommandResponse response ) throws ProcessException;

    public void start() throws ProcessException;

    public void stop() throws ProcessException;


}
