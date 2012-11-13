/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
	 * Raises when some of the child records raise ModelEvent.CHANGED event
	 */
    public static final StoreEvents Changed = new StoreEvents("DataEvents.StoreEvents.Changed");

	/**
	 * Raises when store restores from the backup or any other cases when state implicitly changes.
	 */
	public static final StoreEvents Refresh = new StoreEvents("DataEvents.StoreEvents.Refresh");

}
