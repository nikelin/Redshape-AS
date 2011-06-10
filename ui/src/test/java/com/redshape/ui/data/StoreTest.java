package com.redshape.ui.data;

import com.redshape.ui.data.stores.ListStore;

public class StoreTest extends ListStore<ModelDataTest> {
	private static final long serialVersionUID = -1116713519577510822L;

	public StoreTest() {
		super( new ModelTest() );
	}

}
