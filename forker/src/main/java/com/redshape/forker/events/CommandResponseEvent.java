package com.redshape.forker.events;

import com.redshape.forker.IForkCommandResponse;
import com.redshape.utils.Commons;
import com.redshape.utils.events.AbstractEvent;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/27/12
 * Time: 2:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandResponseEvent extends AbstractEvent {

    public CommandResponseEvent(IForkCommandResponse response) {
        super(Commons.array(response));
        Commons.checkNotNull(response);
    }

    public IForkCommandResponse getResponse() {
        return this.getArg(0);
    }
}
