package com.redshape.forker.commands.handling;

import com.redshape.forker.IForkCommand;
import com.redshape.forker.IForkCommandResponse;
import com.redshape.forker.ProcessException;
import com.redshape.forker.commands.PauseCommand;
import com.redshape.forker.handlers.IForkCommandHandler;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/27/12
 * Time: 2:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class PauseCommandHandler implements IForkCommandHandler {

    @Override
    public boolean isSupported(IForkCommand command) {
        return PauseCommand.class.isAssignableFrom(command.getClass());
    }

    @Override
    public IForkCommandResponse execute(IForkCommand command) throws ProcessException {
        return new PauseCommand.Response(command.getCommandId(), IForkCommandResponse.Status.SUCCESS);
    }
}
