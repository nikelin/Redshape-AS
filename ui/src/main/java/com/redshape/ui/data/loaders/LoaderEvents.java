package com.redshape.ui.data.loaders;

import com.redshape.ui.data.DataEvents;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 21:40
 * To change this template use File | Settings | File Templates.
 */
public class LoaderEvents extends DataEvents {

    public LoaderEvents( String code ) {
        super( code );
    }

    public final static LoaderEvents BeforeLoad = new LoaderEvents("DataEvents.LoaderEvents.BeforeLoad");

    public final static LoaderEvents Loaded = new LoaderEvents("DataEvents.LoaderEvents.Loaded");

    public final static LoaderEvents Error = new LoaderEvents("DataEvents.LoaderEvents.Error");

}
