package com.redshape.ui.events.data.stores;

import com.redshape.ui.events.EventType;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 22:39
 * To change this template use File | Settings | File Templates.
 */
public class StoreEvents extends EventType {

    public StoreEvents( String code ) {
        super(code);
    }

    public static final StoreEvents Backup = new StoreEvents("StoreEvents.Backup");
    
    public static final StoreEvents Restore = new StoreEvents("StoreEvents.Restore");
    
    public static final StoreEvents Clean = new StoreEvents("StoreEvents.Clean");

    public static final StoreEvents Added = new StoreEvents("StoreEvents.Added");

    public static final StoreEvents Removed = new StoreEvents("StoreEvents.Removed");

    public static final StoreEvents Loaded = new StoreEvents("StoreEvents.Loaded");

    public static final StoreEvents BeforeLoaded = new StoreEvents("StoreEvents.BeforeLoaded");

}
