package com.redshape.forker.events;

import com.redshape.utils.events.AbstractEvent;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/30/12
 * Time: 7:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class ExecutorStartedEvent extends AbstractEvent {

    public ExecutorStartedEvent() {
        this( new Object[] {} );
    }

    public ExecutorStartedEvent(Object... args) {
        super(args);
    }
}
