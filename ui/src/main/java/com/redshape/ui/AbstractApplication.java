package com.redshape.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.redshape.ui.components.IComponent;
import com.redshape.ui.components.IComponentsRegistry;
import com.redshape.ui.events.IEventHandler;
import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.UIEvents;
import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;

import javax.swing.*;

/**
 * @author nikelin
 */
public abstract class AbstractApplication {
    private JFrame context;
    private IComponentsRegistry registry;

    public AbstractApplication( AbstractMainWindow context ) {
        this.context = context;
        
        this.context.addWindowListener( new WindowAdapter() {
        	@Override
        	public void windowClosed(WindowEvent e) {
        		super.windowClosed(e);
        		
        		AbstractApplication.this.exit();
        	}
		});
    }
    
    public JFrame getRootContext() {
    	return this.context;
    }
    
    public void exit() {
    	System.exit(1);
    }
    
    public void setComponentsRegistry( IComponentsRegistry registry ) {
    	this.registry = registry;
    }
    
    protected IComponentsRegistry getComponentsRegistry() {
    	return this.registry;
    }
    
    protected void init() {
		Dispatcher.get().addListener( UIEvents.Core.Exit, new IEventHandler() {
			@Override
			public void handle(AppEvent event) {
				System.out.println("Exiting...");
				AbstractApplication.this.exit();
			}
		});
    	
        for ( IComponent component : this.getComponentsRegistry().getComponents() ) {
    		component.init();
    		
    		if ( component.doRenderMenu() ) {
	    		// @todo: move to entitity like MenuBuilder or elsewhere
	    		UIRegistry.getMenu().add( this.createMenu( component ) );
    		}
    	}
    }

    public void start() throws ApplicationException {
    	Dispatcher.get().addListener(UIEvents.Core.Repaint, new IEventHandler() {
            @Override
            public void handle(AppEvent type) {
            	System.out.println("Repainted");
            	AbstractApplication.this.context.invalidate();
            	AbstractApplication.this.context.repaint();
            	
            	JComponent component = UIRegistry.<JComponent>get( UIConstants.CENTER_PANE );
            	component.revalidate();
            	component.invalidate();
            	component.repaint();
            }
        });

        Dispatcher.get().addListener(UIEvents.Core.Init, new IEventHandler() {
            @Override
            public void handle(AppEvent type) {
                AbstractApplication.this.context.setVisible(true);
                Dispatcher.get().forwardEvent( UIEvents.Core.Repaint );
            }
        });

        Dispatcher.get().forwardEvent( UIEvents.Core.Init );
    }

    protected JMenu createMenu( IComponent component ) {
    	JMenu menu = new JMenu();
    	menu.setText( component.getTitle() );
    	
    	for ( Action action : component.getActions() ) {
    		menu.add( action );
    	}
    	
    	return menu;
    }
    
}