package com.redshape.ui.data.loaders;

import com.redshape.ui.application.events.EventDispatcher;
import com.redshape.ui.data.IModelData;

import java.util.Collection;

public abstract class AbstractDataLoader<T extends IModelData>
									extends EventDispatcher
									implements IDataLoader<T> {
	private static final long serialVersionUID = 7205617290953493540L;

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
