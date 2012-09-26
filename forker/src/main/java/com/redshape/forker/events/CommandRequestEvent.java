package com.redshape.forker.events;

import com.redshape.forker.IForkCommand;
import com.redshape.utils.Commons;
import com.redshape.utils.events.AbstractEvent;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/27/12
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandRequestEvent extends AbstractEvent {

    public CommandRequestEvent( IForkCommand command ) {
        super(Commons.array(command));

        Commons.checkNotNull(command);
    }

    public IForkCommand getCommand() {
        return getArg(0);
    }
}
