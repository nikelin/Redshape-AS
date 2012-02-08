package com.redshape.ui.views;

import com.redshape.ui.application.events.IEventHandler;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 07.01.11
 * Time: 2:51
 * To change this template use File | Settings | File Templates.
 */
public interface IView<T> extends IEventHandler, Serializable {
	
    public void init();

    public void unload( T component );
    
    public void render( T component );

}
