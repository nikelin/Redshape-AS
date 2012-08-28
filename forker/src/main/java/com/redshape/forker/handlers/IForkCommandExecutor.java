package com.redshape.forker.handlers;

import com.redshape.forker.IForkCommand;
import com.redshape.forker.IForkCommandResponse;
import com.redshape.forker.ProcessException;
import com.redshape.utils.events.IEventDispatcher;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/27/12
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IForkCommandExecutor extends IEventDispatcher {

    public enum Mode {
        CLIENT,
        SERVER
    }

    public void setMode( Mode mode );

    public Mode getMode();

    public void addHandler( IForkCommandHandler handler );

    public void removeHandler( IForkCommandHandler handler );

    public void clearHandlers();

    public boolean isStarted();

    public void response( IForkCommandResponse command ) throws ProcessException;

    public <T extends IForkCommandResponse> T execute( IForkCommand command ) throws ProcessException;

    public void init( DataInputStream input, DataOutputStream output ) throws ProcessException;

    public void start() throws ProcessException;

    public void stop() throws ProcessException;

    public void acceptInit() throws ProcessException;

}
