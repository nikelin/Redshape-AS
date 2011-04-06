package com.redshape.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
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
    
	public static class AWTUnhandledExceptionsHandler {

		public static void register() {
			System.setProperty("sun.awt.exception.handler", AWTUnhandledExceptionsHandler.class.getName() );
		}
		
		public void handle(Throwable e) {
			Dispatcher.get().forwardEvent( UIEvents.Core.Error, e );
		}
	}
	
	private JFrame context;
	private ApplicationContext applicationContext;

    public AbstractApplication( ApplicationContext applicationContext, AbstractMainWindow context ) {
        this.context = context;
        this.applicationContext = applicationContext;
        
        UIRegistry.setRootContext(context);
        
        this.context.addWindowListener( new WindowAdapter() {
        	@Override
        	public void windowClosed(WindowEvent e) {
        		super.windowClosed(e);
        		
        		Dispatcher.get().forwardEvent(UIEvents.Core.Exit);
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
    	AWTUnhandledExceptionsHandler.register();

    	this.initTrayIcon();
    	
    	Dispatcher.get().addListener(UIEvents.Core.Repaint, new IEventHandler() {
            @Override
            public void handle(AppEvent event) {
            	if ( event.getArg(0) != null && ( event.getArg(0) instanceof JComponent ) ) {
            		log.info("Partial repainting for component: " + event.getArg(0) );
            		JComponent component = event.getArg(0);
            		component.revalidate();
            		component.repaint();
            	} else if ( event.getArg(0) != null && ( event.getArg(0) instanceof UIConstants.Attribute ) ) {
            		log.info("Partial repainting for attribute: " + event.getArg(0) );
            		Object registryObject = UIRegistry.get( event.<UIConstants.Attribute>getArg(0) );
            		// TODO: java.awt.Component needs to be repainted to...
            		if ( registryObject instanceof JComponent ) {
            			final JComponent component = ( (JComponent) registryObject );
            			component.revalidate();
            			component.repaint();
            		}
            	} else {
            		log.info("Full repaint...");
                	for ( UIConstants.Area area : UIConstants.Area.values() ) {
                		JComponent component = UIRegistry.get( area );
                		if ( component == null ) {
                			continue;
                		}
                		
    	            	component.revalidate();
    	            	component.repaint();
                	}

                	AbstractApplication.this.context.invalidate();
                	AbstractApplication.this.context.repaint();
            	}
            }
        });
    	
		Dispatcher.get().addListener( UIEvents.Core.Exit, new IEventHandler() {
			@Override
			public void handle(AppEvent event) {
				if ( JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(AbstractApplication.this.context, "Are you really want to exit?") ) {
					System.out.println("Exiting...");
				
        			AbstractApplication.this.exit();
        		}
			}
		});
		
		Thread.currentThread().setUncaughtExceptionHandler( new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				Dispatcher.get().forwardEvent( UIEvents.Core.Error, e );
			}
		} );
		
		Dispatcher.get().addListener( UIEvents.Core.Error, new IEventHandler() {
			@Override
			public void handle(AppEvent event) {
				Object errorDescription = event.getArg(0);
				if ( errorDescription instanceof Throwable ) {
					Throwable exception = event.getArg(0);
					
					int option = JOptionPane.showConfirmDialog(
							AbstractApplication.this.context, 
							"Some internal exception throwed. See details?", 
							"Error!", 
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.ERROR_MESSAGE );
					
					if ( JOptionPane.YES_OPTION == option ) {
						JOptionPane.showMessageDialog( 
							AbstractApplication.this.context, 
							AbstractApplication.this.stackTraceAsString( exception ) );
					} else if ( JOptionPane.CANCEL_OPTION == option) {
						Dispatcher.get().forwardEvent( UIEvents.Core.Exit );
					}
				} else if ( errorDescription instanceof String ){
					JOptionPane.showMessageDialog( 
						AbstractApplication.this.context,
						String.valueOf( event.getArg(0) ),
						"Error!",
						JOptionPane.ERROR_MESSAGE
					);
				} else {
					JOptionPane.showMessageDialog(
						AbstractApplication.this.context,
						errorDescription != null ? 
								errorDescription.toString()
								: "Unknow exception",
						"Error!",
						JOptionPane.ERROR_MESSAGE
					);
				}
			}
		});
		
        for ( IComponent component : this.getComponentsRegistry().getComponents() ) {
    		this.processComponent(component);
    	}
    }

	protected void processComponent( IComponent component ) {
		this.processComponent(null, component);
	}

	protected void processComponent( JMenu context, IComponent component ) {
		component.init();

		for ( IController controller : component.getControllers() ) {
			Dispatcher.get().addController( controller );
		}

		JMenu menu = null;
		if ( component.doRenderMenu() ) {
			menu = this.createMenu(component);
			if ( context == null ) {
				UIRegistry.getMenu().add( menu );
			} else {
				context.add( menu );
			}
		}

		for ( IComponent child : component.getChildren() ) {
			this.processComponent( menu, child );
		}
	}
    
    abstract protected void initTrayIcon();
    
    private String stackTraceAsString( Throwable e ) {
    	final Writer writer = new StringWriter();
    	final PrintWriter printer = new PrintWriter(writer);
    	
    	e.printStackTrace(printer);
    	
    	return writer.toString();
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