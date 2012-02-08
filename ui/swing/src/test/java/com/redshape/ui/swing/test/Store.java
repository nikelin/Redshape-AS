package com.redshape.ui.swing.test;

import com.redshape.ui.data.stores.ListStore;

public class Store extends ListStore<ModelData> {
	private static final long serialVersionUID = -1116713519577510822L;

	public Store() {
		super( new Model() );
	}

}
