package com.redshape.ui.data.loaders;

import java.util.Collection;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.events.EventDispatcher;

public abstract class AbstractDataLoader<T extends IModelData>
									extends EventDispatcher
									implements IDataLoader<T> {

    @Override
    public Collection<T> preProcess( Collection<T> results ) {
        return results;
    }

	@Override
	public void load() throws LoaderException {
		try { 
			this.forwardEvent( LoaderEvents.BeforeLoad );

			this.forwardEvent( LoaderEvents.Loaded, this.preProcess( this.doLoad() ) );
		} catch ( LoaderException e ) {
			throw e;
		} catch ( Throwable e ) {
			this.forwardEvent( LoaderEvents.Error );
		}
	}
	
	protected abstract Collection<T> doLoad() throws LoaderException;

}
