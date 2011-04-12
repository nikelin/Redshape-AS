package com.redshape.ui.data.stores;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.redshape.ui.data.IStore;

public class StoresManager implements IStoresManager {
	private Collection<StoreContext> contexts = new HashSet<StoreContext>();
	private StoreContext globalContext = new StoreContext(this);

	public boolean isRegistered( Object context ) {
		for ( StoreContext registered : this.contexts ) {
			if ( registered.getContext().equals(context) ) {
				return true;
			}
		}

		return false;
	}

	protected StoreContext getContext( Object context ) {
		return this.getContext( context, false );
	}

	protected StoreContext getContext( Object context, boolean forceCreation ) {
		for ( StoreContext registered : this.contexts ) {
			if ( registered.getContext().equals(context) ) {
				return registered;
			}
		}

		StoreContext result = this.globalContext;
		if ( forceCreation && !context.equals(this) ) {
			this.contexts.add( result = new StoreContext(context) );
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IStore<?>> T getStore( Class<? extends T> clazz ) throws InstantiationException {
		return this.getStore( this, clazz );
	}

	public <T extends IStore<?>> T getStore( Object context, Class<? extends T> clazz ) throws InstantiationException {
		if ( clazz == null ) {
			throw new IllegalArgumentException("null");
		}

		StoreContext storeContext = this.getContext(context, true);

		T store = storeContext.getItem(clazz);
		if ( store != null ) {
			return store;
		}

		storeContext.addItem( store = (T) this.createStoreInstance(clazz) );

		return store;
	}
	
	protected IStore<?> createStoreInstance( Class<? extends IStore<?>> clazz ) throws InstantiationException {
		if ( clazz == null ) {
			throw new IllegalArgumentException("null");
		}
		
		IStore<?> storeInstance;
		try {
			storeInstance = clazz.newInstance();
		} catch ( Throwable e ) {
			throw new InstantiationException( e.getMessage() );
		}
		
		return storeInstance;
	}

	public <T extends IStore<?>> void register( T store ) {
		this.register( this, store);
	}

	@Override
	public <T extends IStore<?>> void register( Object context, T store ) {
		this.getContext( context, true ).addItem( store );
	}

	@Override
	public Collection<IStore<?>> list() {
		Collection<IStore<?>> stores = new HashSet<IStore<?>>();
		for ( StoreContext context : this.contexts ) {
			stores.addAll( context.list() );
		}

		return stores;
	}

	@Override
	public Collection<IStore<?>> list( Object context ) {
		if ( !this.isRegistered(context) ) {
			return new HashSet<IStore<?>>();
		}

		return this.getContext(context).list();
	}

	protected class StoreContext {
		private Object context;
		private Map<Class<? extends IStore<?>>, IStore<?>> items =
					new HashMap<Class<? extends IStore<?>>, IStore<?>>();

		public StoreContext( Object context ) {
			this.context = context;
		}

		public Object getContext() {
			return this.context;
		}

		public <T extends IStore<?>> T getItem( Class<? extends T> clazz ) {
			return (T) this.items.get(clazz);
		}

		public void addItem( IStore<?> store ) {
			this.items.put( (Class<? extends IStore<?>>) store.getClass(), store );
		}

		public Collection<IStore<?>> list() {
			Collection<IStore<?>> stores = new HashSet<IStore<?>>();
			stores.addAll( this.items.values() );
			return stores;
		}

	}
	
}
