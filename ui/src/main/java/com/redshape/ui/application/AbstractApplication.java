package com.redshape.ui.application;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.application.events.*;
import com.redshape.ui.application.events.handlers.ErrorsHandler;
import com.redshape.ui.application.handlers.ExitHandler;
import com.redshape.ui.application.handlers.RepaintHandler;
import com.redshape.ui.application.handlers.awt.AWTExceptionsHandler;
import com.redshape.ui.components.IComponent;
import com.redshape.ui.components.IComponentsRegistry;
import com.redshape.ui.components.actions.ComponentAction;
import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;
import com.redshape.ui.views.widgets.IWidget;
import com.redshape.ui.views.widgets.IWidgetsManager;
import com.redshape.ui.windows.AbstractMainWindow;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;
import com.redshape.utils.events.AbstractEventDispatcher;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Collection;

/**
 * @author nikelin
 */
public abstract class AbstractApplication extends EventDispatcher implements IApplication {
	private static final Logger log = Logger.getLogger( AbstractApplication.class );

    public static class Events extends EventType {
        protected Events(String code) {
            super(code);
        }
        
        public static final Events Start = new Events("Application.Events.Started");
        public static final Events Stop = new Events("Application.Events.Stop");
    }
    
	private JFrame context;
	private ApplicationContext applicationContext;

    public AbstractApplication( ApplicationContext applicationContext,
                                AbstractMainWindow context ) throws ApplicationException {
        this.context = context;
        this.applicationContext = applicationContext;
        
        UIRegistry.setRootContext(context);
        UIRegistry.set( UIConstants.System.SPRING_CONTEXT, applicationContext );
        
        this.context.addWindowListener( new WindowAdapter() {
        	@Override
        	public void windowClosed(WindowEvent e) {
        		super.windowClosed(e);
        		
        		Dispatcher.get().forwardEvent(UIEvents.Core.Exit);
        	}
		});

        this.init();
    }

    protected static ApplicationContext loadContext( String contextPath ) {
        File file = new File(contextPath);
        if (file.exists()) {
            return new FileSystemXmlApplicationContext(contextPath);
        } else {
            return new ClassPathXmlApplicationContext(contextPath);
        }
    }
    
    @Override public void exit() {
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

    protected IConfig getConfig() {
        return this.getContext().getBean( IConfig.class );
    }
    
    protected void init() throws ApplicationException {
    	this.initAWTExceptionsHandler();

    	this.initTrayIcon();

    	Dispatcher.get().addListener( UIEvents.Core.Repaint, this.createRepaintHandler() );
		Dispatcher.get().addListener( UIEvents.Core.Exit, this.createExitHandler() );
		
		Thread.currentThread().setUncaughtExceptionHandler( this.createUncaughtHandler() );
		
		Dispatcher.get().addListener( UIEvents.Core.Error, this.createErrorsHandler() );
		
        for ( IComponent component : this.getComponentsRegistry().getComponents() ) {
    		this.processComponent(component);
    	}

        try {
            this.initInterceptors();
        } catch ( ConfigException e ) {
            throw new ApplicationException("Interceptors init failed");
        }
    }

	protected void processComponent( IComponent component ) {
		this.processComponent(null, component);
	}

	protected void processComponent( Menu context, IComponent component ) {
		component.init();

		for ( IController controller : component.getControllers() ) {
			Dispatcher.get().addController( controller );
		}

		Menu menu = null;
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

    protected void initInterceptors() throws ConfigException {
        IConfig listNode = this.getConfig().get("interceptors");
        if ( listNode.isNull() ) {
            return;
        }

        for ( IConfig node : listNode.childs() ) {
            String signal = node.get("signal").value();
            String className = node.get("class").value();
            if ( className == null ) {
                continue;
            }

            try {
                Class<?> clazz = Class.forName( className);
                if ( ! IEventHandler.class.isAssignableFrom(clazz) ) {
                    continue;
                }

                Dispatcher.get().addListener( EventType.valueOf( signal ), (IEventHandler) clazz.newInstance() );
            } catch ( Throwable e ) {
                log.error( e.getMessage(), e );
                log.error("Unable to initialized interceptor " + className);
            }
        }
    }

    protected void initTrayIcon() {
    	
    }

    @Override
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
			private static final long serialVersionUID = 2748988068351419365L;

			@Override
            public void handle(AppEvent type) {
                AbstractApplication.this.context.setVisible(true);
                Dispatcher.get().forwardEvent( UIEvents.Core.Repaint );
            }
        });

        Dispatcher.get().forwardEvent( UIEvents.Core.Init );
        
        this.forwardEvent( Events.Start );
    }

    protected Menu createMenu( IComponent component ) {
    	Menu menu = new Menu();
    	menu.setLabel( component.getTitle() );
    	
    	for ( final Action action : component.getActions() ) {
            if ( action instanceof ComponentAction.Empty ) {
                menu.addSeparator();
            } else {
            	MenuItem item = new MenuItem( String.valueOf( action.getValue( Action.NAME ) ) );
            	item.addActionListener( new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						action.actionPerformed(e);
					}
				});
            	
    		    menu.add( item );
            }
    	}
    	
    	return menu;
    }

    protected Thread.UncaughtExceptionHandler createUncaughtHandler() {
        return new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				Dispatcher.get().forwardEvent( UIEvents.Core.Error, e );
			}
		};
    }

    protected void initAWTExceptionsHandler() {
        AWTExceptionsHandler.register();
    }

    protected IEventHandler createErrorsHandler() {
        return new ErrorsHandler(this);
    }

    protected IEventHandler createExitHandler() {
        return new ExitHandler(this);
    }

    protected IEventHandler createRepaintHandler() {
        return new RepaintHandler(this);
    }
    
}