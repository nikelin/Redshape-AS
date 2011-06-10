package com.redshape.ui.application.events;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 03.01.11
 * Time: 21:46
 * To change this template use File | Settings | File Templates.
 */
public interface IEventHandler extends Serializable {

    public void handle( AppEvent event );

}
