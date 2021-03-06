/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.ui.windows;

import com.redshape.ui.application.UnhandledUIException;
import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;
import com.redshape.utils.IFilter;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

// rework
public class WindowsManager implements ISwingWindowsManager {
	private Map<Class<? extends JFrame>, JFrame> registry = new HashMap<Class<? extends JFrame>, JFrame>();
	private static final ClosedFilter closedFilter = new ClosedFilter();
	private JFrame focusedWindow;

    protected <T extends JFrame> T register( Class<T> windowClazz ) {
        T result;
        try {
			result = windowClazz.newInstance();
		} catch ( Throwable e ) {
			throw new UnhandledUIException( e.getMessage(), e );
		}

        return this.register( result );
    }

    @Override
    public void close( final Class<? extends JFrame> windowClazz) {
        for ( JFrame window : this.filter( new ClassFilter(windowClazz) ) ) {
            this.close(window);
        }
    }

    protected <T extends JFrame> T register( T window ) {
        window.addWindowFocusListener( new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent windowEvent) {
                // WindowsManager.this.focusedWindow = windowEvent.getWindow();
            }

            @Override
            public void windowLostFocus(WindowEvent windowEvent) {
                // WindowsManager.this.focusedWindow = windowEvent.getOppositeWindow();
            }
        });

		this.registry.put( window.getClass(), window );
        return window;
    }

    @Override
    public JFrame getFocusedWindow() {
        if ( this.focusedWindow == null) {
            return UIRegistry.<JFrame>get(UIConstants.System.WINDOW);
        }

        return this.focusedWindow;
    }

	@SuppressWarnings("unchecked")
	@Override
	public <T extends JFrame> T get( Class<T> clazz ) {
		T result = (T) this.registry.get(clazz);
		if ( result != null ) {
			result.setVisible(true);
			return result;
		}
		
        result = this.register(clazz);
		
		return (T) result;
	}
	
	@Override
	public void open( JFrame window ) {
        if ( !this.isRegistered(window) ) {
            this.register( window );
        }

        this.focusedWindow = window;

		window.setVisible(true);
        window.getLayeredPane().moveToFront( window );
	}
	
	@Override
	public <T extends JFrame> T open(Class<T> clazz) {
		T result = this.get(clazz);
		
		this.open(result);
		
		return result;
	}

	@Override
	public void destory(JFrame window) {
		if ( !this.isRegistered(window) ) {
			return;
		}

        this.focusedWindow = null;

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

	@Override
	public Collection<JFrame> list() {
		return this.registry.values();
	}
	
    private static class ClassFilter implements IFilter<JFrame> {
        private Class<? extends JFrame> clazz;
        
        public ClassFilter( Class<? extends JFrame> clazz ) {
            this.clazz = clazz;
        }
        
        @Override
        public boolean filter( JFrame filterable ) {
            return clazz.isAssignableFrom( filterable.getClass() );
        }
        
    }
    
	private static class ClosedFilter implements IFilter<JFrame> {
		@Override
		public boolean filter(JFrame filterable) {
			return !filterable.isVisible();
		}
	}

}
