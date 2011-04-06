package com.redshape.ui.data.loaders;

import java.util.List;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.events.EventDispatcher;

public abstract class AbstractDataLoader<T extends IModelData>
									extends EventDispatcher
									implements IDataLoader<T> {

	@Override
	public void load() throws LoaderException {
		try { 
			this.forwardEvent( LoaderEvents.BeforeLoad );
			this.forwardEvent( LoaderEvents.Loaded, this.doLoad() );
		} catch ( LoaderException e ) {
			throw e;
		} catch ( Throwable e ) {
			this.forwardEvent( LoaderEvents.Error );
		}
	}
	
	protected abstract List<T> doLoad() throws LoaderException;

}
