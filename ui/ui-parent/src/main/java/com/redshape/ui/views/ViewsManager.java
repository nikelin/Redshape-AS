package com.redshape.ui.views;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.application.events.UIEvents;
import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Default views manager implementation
 * 
 * @author nikelin
 */
public class ViewsManager implements IViewsManager {
	private Map<Object, IView> views = new HashMap<Object, IView>();
	private Collection<Object> initializedViews = new HashSet<Object>();
	private UIConstants.Area area;
	private IView activeView;
	
	public ViewsManager( UIConstants.Area area ) {
		this.area = area;
	}
	
	@Override
	public void activate(Object id) throws ViewException {
		if ( !this.isRegistered(id) ) {
			throw new ViewException("View " + id + " not registered " +
					"in current manager instance");
		}
		
		IView view = this.getView(id);
		if ( this.activeView == view ) {
			return;
		}
		
		this.deactivate();
		
		if ( !this.initializedViews.contains(id) ) {
			view.init();
			this.initializedViews.add(id);
		}
			
		view.render( this.getViewArea() );
		
		Dispatcher.get().forwardEvent( UIEvents.Core.Repaint, this.getViewArea() );
		
		this.activeView = view;
	}

	@Override
	public void deactivate() {
		if ( this.activeView == null ) {
			return;
		}
		
		this.activeView.unload( this.getViewArea() );
		this.activeView = null;
	}

	@Override
	public boolean isRegistered( Object id ) {
		return this.views.containsKey(id);
	}
	
	@Override
	public void register(IView view, Object id) {
		if ( this.isRegistered(view) ) {
			this.unregister(id);
		}
		
		this.views.put(id, view);
	}

	@Override
	public void unregister(Object id) {
		IView view = this.getView(id);
		if ( view == null ) {
			return;
		}
		
		if ( this.activeView == view ) {
			this.activeView.unload( this.getViewArea() );
		}
		
		this.views.remove( view );
		this.initializedViews.remove( id );
	}
	
	protected IView getView( Object id ) {
		return this.views.get(id);
	}

	@Override
	public IView getActive() {
		return this.activeView;
	}

	@Override
	public void setViewArea( UIConstants.Area area) {
		if ( area == null ) {
			throw new IllegalArgumentException("null");
		}
		
		this.area = area;
	}

	protected <T> T getViewArea() {
		return UIRegistry.get( area );
	}

}
