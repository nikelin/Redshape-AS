package com.redshape.ui;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;

import com.redshape.ui.components.IComponent;
import com.redshape.ui.components.IComponentsRegistry;
import com.redshape.ui.events.IEventHandler;
import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.UIEvents;
import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;
import com.redshape.ui.widgets.IWidget;
import com.redshape.ui.widgets.IWidgetsManager;

import javax.swing.*;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

/**
 * @author nikelin
 */
public abstract class AbstractApplication {
	private static final Logger log = Logger.getLogger( AbstractApplication.class );
    
	private JFrame context;
	private ApplicationContext applicationContext;
    private IComponentsRegistry registry;

    public AbstractApplication( ApplicationContext applicationContext, AbstractMainWindow context ) {
        this.context = context;
        this.applicationContext = applicationContext;
        
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

    private ApplicationContext getContext() {
    	return this.applicationContext;
    }
    
    protected IComponentsRegistry getComponentsRegistry() {
    	return this.getContext().getBean( IComponentsRegistry.class );
    }
    
    protected IWidgetsManager getWidgetsManager() {
    	return this.getContext().getBean( IWidgetsManager.class );
    }
    
    protected void init() throws ApplicationException {
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
		IWidgetsManager manager = this.getWidgetsManager(); 
		for ( UIConstants.Area area : UIConstants.Area.values() ) {
			Collection<IWidget> widgets = manager.getWidgets(area);
			if ( widgets == null || widgets.isEmpty() ) {
				continue;
			}
			
			for ( IWidget widget : widgets ) {
				widget.init();
				
				JComponent component = UIRegistry.get(area);
				if ( component != null ) {
					widget.render(component);
				} else {
					log.info("Requested area (" + area.name() + ") does not registered within UIRegistry...");
				}
			}
		}

    	Dispatcher.get().addListener(UIEvents.Core.Repaint, new IEventHandler() {
            @Override
            public void handle(AppEvent type) {
            	System.out.println("Repainted");
            	AbstractApplication.this.context.invalidate();
            	AbstractApplication.this.context.repaint();
            	
            	JComponent component = UIRegistry.<JComponent>get( UIConstants.Area.CENTER );
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