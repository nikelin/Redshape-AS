package com.redshape.ui.data.stores;

import com.redshape.ui.data.DataEvents;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 22:39
 * To change this template use File | Settings | File Templates.
 */
public class StoreEvents extends DataEvents {

    protected StoreEvents( String code ) {
        super(code);
    }

    /**
     * Raises when someone attempts to take snapshot of store
     */
    public static final StoreEvents Backup = new StoreEvents("DataEvents.StoreEvents.Backup");
    
    /**
     * Raises when someone attempts to recover store state from backup
     */
    public static final StoreEvents Restore = new StoreEvents("DataEvents.StoreEvents.Restore");
    
    /**
     * Raises every store cleanup ( clean() method invoke )
     */
    public static final StoreEvents Clean = new StoreEvents("DataEvents.StoreEvents.Clean");

    /**
     * Raises when new record added to store
     */
    public static final StoreEvents Added = new StoreEvents("DataEvents.StoreEvents.Added");

    /**
     * Raises when some record removed from store 
     */
    public static final StoreEvents Removed = new StoreEvents("DataEvents.StoreEvents.Removed");

    /**
     * Raises after store records collection filled through data loader
     */
    public static final StoreEvents Loaded = new StoreEvents("DataEvents.StoreEvents.Loaded");

    /**
     * Raises before load invoke being transfered to loader
     */
    public static final StoreEvents BeforeLoaded = new StoreEvents("DataEvents.StoreEvents.BeforeLoaded");

    /**
     * Raises when some of the child records raise ModelEvent.CHANGED event
     */
    public static final StoreEvents Changed = new StoreEvents("DataEvents.StoreEvents.Changed");
}
