package com.redshape.ui;

import java.awt.event.ActionListener;

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
    }
    
    public void setComponentsRegistry( IComponentsRegistry registry ) {
    	this.registry = registry;
    }
    
    protected IComponentsRegistry getComponentsRegistry() {
    	return this.registry;
    }

    public void start() throws ApplicationException {
    	Dispatcher.get().addListener(UIEvents.Core.Repaint, new IEventHandler() {
            @Override
            public void handle(AppEvent type) {
                JComponent context = ( (JComponent) UIRegistry.get(UIConstants.CENTER_PANE) );
	            context.revalidate();
	            context.repaint();
            }
        });

        Dispatcher.get().addListener(UIEvents.Core.Init, new IEventHandler() {
            @Override
            public void handle(AppEvent type) {
                AbstractApplication.this.context.setVisible(true);
            }
        });
        
        for ( IComponent component : this.getComponentsRegistry().getComponents() ) {
    		component.init();
    		
    		// @todo: move to entitity like MenuBuilder or elsewhere
    		UIRegistry.getMenu().add( this.createMenu( component ) );
    	}

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