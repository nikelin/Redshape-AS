package com.redshape.ui;

import com.redshape.ui.events.EventType;
import com.redshape.ui.events.IEventHandler;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 07.01.11
 * Time: 2:51
 * To change this template use File | Settings | File Templates.
 */
public interface IView<T extends EventType> extends IEventHandler<T> {

    public void init();

    public void unload();

}
