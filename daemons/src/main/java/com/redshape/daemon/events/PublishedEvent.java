package com.redshape.daemon.events;

import com.redshape.utils.events.AbstractEvent;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/14/11
 * Time: 1:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class PublishedEvent extends AbstractEvent {

    public PublishedEvent() {
        this( new Object[] {} );
    }

    public PublishedEvent(Object... args) {
        super(args);
    }
}
