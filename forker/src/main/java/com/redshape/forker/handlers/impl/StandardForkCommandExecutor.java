package com.redshape.forker.handlers.impl;

import com.redshape.forker.IForkCommand;
import com.redshape.forker.IForkCommandResponse;
import com.redshape.forker.ProcessException;
import com.redshape.forker.commands.ErrorResponse;
import com.redshape.forker.commands.InitResponse;
import com.redshape.forker.events.CommandRequestEvent;
import com.redshape.forker.events.CommandResponseEvent;
import com.redshape.forker.handlers.IForkCommandExecutor;
import com.redshape.forker.handlers.IForkCommandHandler;
import com.redshape.forker.protocol.IForkProtocol;
import com.redshape.utils.Commons;
import com.redshape.utils.events.AbstractEventDispatcher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/27/12
 * Time: 1:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class StandardForkCommandExecutor extends AbstractEventDispatcher implements IForkCommandExecutor {

    public enum State {
        INIT,
        START,
        STOP
    }

    private State state;
    private Mode mode;
    private Collection<IForkCommandHandler> handlers = new ArrayList<IForkCommandHandler>();
    private IForkProtocol protocol;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public StandardForkCommandExecutor( IForkProtocol protocol,
                                        Collection<IForkCommandHandler> handlers) {
        Commons.checkNotNull(handlers);
        Commons.checkNotNull(protocol);

        this.mode = Mode.CLIENT;
        this.state = State.STOP;
        this.protocol = protocol;
        this.handlers = handlers;
    }

    @Override
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public Mode getMode() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addHandler(IForkCommandHandler handler) {
        this.handlers.add(handler);
    }

    @Override
    public void removeHandler(IForkCommandHandler handler) {
        this.handlers.remove(handler);
    }

    @Override
    public void clearHandlers() {
        this.handlers.clear();
    }

    @Override
    public boolean isStarted() {
        return this.state.equals( State.START );
    }

    @Override
    public void init( DataInputStream input, DataOutputStream output ) {
        this.state = State.INIT;
        this.inputStream = input;
        this.outputStream = output;
    }

    @Override
    public void start() throws ProcessException {
        if ( !this.state.equals(State.INIT) ) {
            throw new IllegalStateException("Illegal state exception: " + this.state );
        }

        this.state = State.START;

        if ( this.mode.equals(Mode.CLIENT) ) {
            while ( this.isStarted() ) {
                try {
                    IForkCommand command = this.protocol.readCommand(this.inputStream);
                    this.raiseEvent( new CommandRequestEvent(command) );

                    IForkCommandResponse response = this.executeCommand(command);
                    if ( response == null ) {
                        response = new ErrorResponse("Unsupported command requested", IForkCommandResponse.Status.FAIL);
                    }

                    this.raiseEvent( new CommandResponseEvent(response) );

                    this.protocol.writeResponse(outputStream, response);
                } catch ( IOException e ) {
                    continue;
                }
            }
        }
    }


    @Override
    public <T extends IForkCommandResponse> T execute(IForkCommand command) throws ProcessException {
        try {
            Commons.checkNotNull(command);

            this.raiseEvent( new CommandRequestEvent(command));
            this.protocol.writeCommand( this.outputStream, command );

            IForkCommandResponse response = this.protocol.readResponse( this.inputStream );

            this.raiseEvent( new CommandResponseEvent(response) );

            return (T) response;
        } catch ( IOException e ) {
            throw new ProcessException( e.getMessage(), e);
        }
    }

    protected IForkCommandResponse executeCommand( IForkCommand command ) throws ProcessException {
        Commons.checkNotNull(command);

        IForkCommandHandler targetHandler = null;
        for ( IForkCommandHandler handler : this.handlers ) {
            if ( handler.isSupported(command) ) {
                targetHandler = handler;
                break;
            }
        }

        if ( targetHandler == null ) {
            return null;
        }

        return targetHandler.execute(command);
    }

    @Override
    public void response(IForkCommandResponse command) throws ProcessException {
        try {
            this.protocol.writeResponse(this.outputStream, command);
        } catch ( IOException e ) {
            throw new ProcessException( e.getMessage(), e );
        }
    }

    @Override
    public void acceptInit() throws ProcessException {
        try {
            InitResponse response = this.protocol.readResponse(this.inputStream);
            if ( response == null ) {
                throw new IllegalStateException("<NULL>");
            }
        } catch ( IOException e ) {
            throw new ProcessException( e.getMessage(), e);
        }
    }

    @Override
    public void stop() {
        this.state = State.STOP;
    }
}
