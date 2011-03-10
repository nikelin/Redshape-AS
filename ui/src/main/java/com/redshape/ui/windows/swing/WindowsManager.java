package com.redshape.ui.windows.swing;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.JFrame;

import com.redshape.ui.UnhandledUIException;
import com.redshape.ui.windows.IWindowsManager;
import com.redshape.utils.IFilter;

public class WindowsManager implements IWindowsManager<JFrame> {
	private Map<Class<? extends JFrame>, JFrame> registry = new HashMap<Class<? extends JFrame>, JFrame>();
	private static final ClosedFilter closedFilter = new ClosedFilter();
	
	@Override
	public JFrame open(Class<? extends JFrame> clazz) {
		JFrame result = this.registry.get(clazz);
		if ( result != null ) {
			result.setVisible(true);
			return result;
		}
		
		try {
			result = clazz.newInstance();
		} catch ( Throwable e ) {
			throw new UnhandledUIException( e.getMessage(), e );
		}
		
		this.registry.put( clazz, result);
		result.setVisible(true);
		
		return result;
	}

	@Override
	public void destory(JFrame window) {
		if ( !this.isRegistered(window) ) {
			return;
		}
		
		this.registry.remove( window.getClass() );
	}
	
	@Override
	public boolean isRegistered( JFrame window ) {
		return this.registry.containsKey( window.getClass() );
	}
	
	@Override
	public void close( JFrame frame ) {
		if ( !this.isRegistered(frame) ) {
			return;
		}
		
		frame.setVisible(false);
	}

	@Override
	public Collection<JFrame> getOpened() {
		Collection<JFrame> result = new HashSet<JFrame>();
		
		for ( JFrame frame : this.registry.values() ) {
			if ( !closedFilter.filter(frame) ) {
				result.add( frame );
			}
		}
		
		return result;
	}

	@Override
	public Collection<JFrame> getClosed() {
		Collection<JFrame> result = new HashSet<JFrame>();
		
		for ( JFrame frame : this.registry.values() ) {
			if ( closedFilter.filter(frame) ) {
				result.add( frame );
			}
		}
		
		return result;
	}

	@Override
	public void closeAll() {
		for ( JFrame frame : this.registry.values() ) {
			this.close(frame);
		}
	}

	@Override
	public Collection<JFrame> filter(IFilter<JFrame> filter) {
		Collection<JFrame> result = new HashSet<JFrame>();
		for ( JFrame frame : this.registry.values() ) {
			if ( filter.filter(frame) ) {
				result.add( frame );
			}
		}
		
		return result;
	}
	
	private static class ClosedFilter implements IFilter<JFrame> {
		@Override
		public boolean filter(JFrame filterable) {
			return !filterable.isVisible();
		}
	}

}
