package com.redshape.ui.application;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.application.events.*;
import com.redshape.ui.components.IComponent;
import com.redshape.ui.components.IComponentsRegistry;
import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;
import com.redshape.ui.views.widgets.IWidget;
import com.redshape.ui.views.widgets.IWidgetsManager;
import com.redshape.utils.Commons;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;
import org.apache.log4j.Logger;

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

    private IBeansProvider appContext;

    public AbstractApplication( IBeansProvider applicationContext ) throws ApplicationException {
        super();
        Commons.checkNotNull(applicationContext, 
                "Application context must not be null!");

        UIRegistry.set( UIConstants.System.APP_CONTEXT, applicationContext );

        this.appContext = applicationContext;

        this.init();
    }
    
    @Override
    public void exit() {
    	System.exit(1);
    }

    protected IBeansProvider getContext() {
        return this.appContext;
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
        Thread.currentThread().setUncaughtExceptionHandler( this.createUncaughtHandler() );

        for ( IComponent component : this.getComponentsRegistry().getComponents() ) {
    		this.initComponent(component);
    	}

        try {
            this.initInterceptors();
        } catch ( ConfigException e ) {
            throw new ApplicationException("Interceptors init failed", e );
        }
    }

    protected void initInterceptors() throws ConfigException, ApplicationException {
        IConfig listNode = this.getConfig().get("interceptors");
        if ( listNode.isNull() ) {
            return;
        }

        for ( IConfig node : listNode.childs() ) {
            this.initInterceptor(node);
        }
    }
    
    protected void initInterceptor( IConfig node ) throws ConfigException, ApplicationException {
        String signal = node.get("signal").value();
        String className = node.get("class").value();
        if ( className == null ) {
            return;
        }

        try {
            Class<?> clazz = this.getClass().getClassLoader().loadClass( className );
            if ( ! IEventHandler.class.isAssignableFrom(clazz) ) {
                return;
            }

            Dispatcher.get().addListener(
                EventType.valueOf( signal ),
                (IEventHandler) clazz.newInstance()
            );
        } catch ( Throwable e ) {
            if ( e instanceof ApplicationException ) {
                throw (ApplicationException) e;
            }

            log.error("Unable to initialized interceptor " + className, e);
        }
    }

    protected abstract void initComponent( IComponent component );

    protected abstract void initWidget( UIConstants.Area area, IWidget widget );

    @Override
    public void start() throws ApplicationException {
		IWidgetsManager manager = this.getWidgetsManager(); 
		for ( UIConstants.Area area : UIConstants.Area.values() ) {
			Collection<IWidget> widgets = manager.getWidgets(area);
			if ( widgets == null || widgets.isEmpty() ) {
				continue;
			}
			
			for ( IWidget widget : widgets ) {
				this.initWidget(area, widget);
			}
		}

        Dispatcher.get().forwardEvent( UIEvents.Core.Init );
        
        this.forwardEvent( Events.Start );
    }

    protected Thread.UncaughtExceptionHandler createUncaughtHandler() {
        return new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				Dispatcher.get().forwardEvent( UIEvents.Core.Error, e );
			}
		};
    }

}