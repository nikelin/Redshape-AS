package com.redshape.ui.data;

import com.redshape.ui.data.stores.ListStore;

public class StoreTest extends ListStore<ModelDataTest> {

	public StoreTest() {
		super( new ModelTest() );
	}

}
