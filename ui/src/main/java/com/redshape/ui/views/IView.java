package com.redshape.ui.views;

import java.awt.Container;
import java.io.Serializable;

import com.redshape.ui.events.IEventHandler;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 07.01.11
 * Time: 2:51
 * To change this template use File | Settings | File Templates.
 */
public interface IView extends IEventHandler, Serializable {
	
    public void init();

    public void unload( Container component );
    
    public void render( Container component );

}
