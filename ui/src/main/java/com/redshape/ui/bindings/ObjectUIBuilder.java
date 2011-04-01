package com.redshape.ui.bindings;

import java.util.HashMap;
import java.util.Map;

public class ObjectUIBuilder implements IObjectUIBuilder {
	private Map<Class<?>, IObjectUI<?>> objects = 
						new HashMap<Class<?>, IObjectUI<?>>();
	
	@Override
	public <T> IObjectUI<T> createUI(Class<T> type) {
		return this.createUI( type, false );
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> IObjectUI<T> createUI(Class<T> type, boolean forceCreation) {
		IObjectUI<T> ui = (IObjectUI<T>) this.objects.get( type );
		if ( ui != null && !forceCreation ) {
			return ui;
		}
		
		ui = new ObjectUI<T>( type );
		
		this.objects.put( type, ui );
		
		return ui;
	}

}
