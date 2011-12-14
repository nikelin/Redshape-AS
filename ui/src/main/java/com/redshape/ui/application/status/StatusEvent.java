package com.redshape.ui.application.status;

import com.redshape.ui.application.events.UIEvents;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/14/11
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class StatusEvent extends UIEvents {

    protected StatusEvent(String code) {
        super(code);
    }
    
    public static final StatusEvent Status = new StatusEvent("StatusEvent.Status");
}
