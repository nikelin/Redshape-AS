package com.redshape.ui.data.loaders;

import com.redshape.ui.events.EventType;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 21:40
 * To change this template use File | Settings | File Templates.
 */
public class LoaderEvents extends EventType {

    public LoaderEvents( String code ) {
        super( code );
    }

    public final static LoaderEvents BeforeLoad = new LoaderEvents("LoaderEvents.BeforeLoad");

    public final static LoaderEvents Loaded = new LoaderEvents("LoaderEvents.Loaded");

    public final static LoaderEvents Error = new LoaderEvents("LoaderEvents.Error");

}
