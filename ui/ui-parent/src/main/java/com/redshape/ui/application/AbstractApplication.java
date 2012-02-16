package com.redshape.ui.application;

import com.redshape.ui.Dispatcher;
import com.redshape.ui.application.events.EventDispatcher;
import com.redshape.ui.application.events.EventType;
import com.redshape.ui.application.events.UIEvents;
import com.redshape.ui.components.IComponent;
import com.redshape.ui.components.IComponentsRegistry;
import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;
import com.redshape.ui.views.widgets.IWidget;
import com.redshape.ui.views.widgets.IWidgetsManager;
import com.redshape.utils.Commons;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;

import java.util.Collection;

/**
 * @author nikelin
 */
public abstract class AbstractApplication extends EventDispatcher implements IApplication {

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

    /**
     * With a mind of GWT-compiler (which do not understand Class.isAssignableFrom)
     * @param source
     * @param target
     * @return
     */
    protected abstract boolean isAssignableFrom( Class<?> source, Class<?> target );
    
    protected void init() throws ApplicationException {
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
    
    protected abstract void initInterceptor( IConfig node ) throws ConfigException, ApplicationException;

    protected abstract void initComponent( IComponent<?> component );

    protected abstract void initWidget( UIConstants.Area area, IWidget<?> widget );

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

}