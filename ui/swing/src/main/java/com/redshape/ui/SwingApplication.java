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

package com.redshape.ui;

import com.redshape.ui.application.*;
import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.application.events.EventType;
import com.redshape.ui.application.events.IEventHandler;
import com.redshape.ui.application.events.UIEvents;
import com.redshape.ui.application.events.handlers.ErrorsHandler;
import com.redshape.ui.application.handlers.ExitHandler;
import com.redshape.ui.application.handlers.RepaintHandler;
import com.redshape.ui.application.handlers.awt.AWTExceptionsHandler;
import com.redshape.ui.components.IComponent;
import com.redshape.ui.components.actions.ComponentAction;
import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;
import com.redshape.ui.views.widgets.IWidget;
import com.redshape.ui.windows.AbstractMainWindow;
import com.redshape.utils.Commons;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.ui
 * @date 2/7/12 {8:11 PM}
 */
public class SwingApplication extends AbstractApplication {
    private static final Logger log = Logger.getLogger(SwingApplication.class);

    private AbstractMainWindow context;

    public SwingApplication( AbstractMainWindow window,
                             IBeansProvider provider ) throws ApplicationException {
        super( provider );

        Commons.checkNotNull(window);

        UIRegistry.set( UIConstants.System.APP_CONTEXT, provider );
        
        this.context = window;
        this.context.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);

                Dispatcher.get().forwardEvent(UIEvents.Core.Exit);
            }
        });
    }

    @Override
    protected void init() throws ApplicationException{
        super.init();

        Thread.currentThread().setUncaughtExceptionHandler( this.createUncaughtHandler() );

        this.initAWTExceptionsHandler();

        Dispatcher.get().addListener( UIEvents.Core.Repaint, this.createRepaintHandler() );
        Dispatcher.get().addListener( UIEvents.Core.Exit, this.createExitHandler() );
        Dispatcher.get().addListener( UIEvents.Core.Error, this.createErrorsHandler() );
    }

    @Override
    protected void initInterceptor( IConfig node ) throws ConfigException, ApplicationException {
        String signal = node.get("signal").value();
        String className = node.get("class").value();
        if ( className == null ) {
            return;
        }

        try {
            Class<?> clazz = this.getContext().getBean(node.name());
            if ( ! this.isAssignableFrom(IEventHandler.class, clazz) ) {
                return;
            }

            Dispatcher.get().addListener(
                EventType.valueOf(signal),
                (IEventHandler) clazz.newInstance()
            );
        } catch ( Throwable e ) {
            if ( e instanceof ApplicationException ) {
                throw (ApplicationException) e;
            }

            log.error("Unable to initialized interceptor " + className, e);
        }
    }

    @Override
    protected boolean isAssignableFrom(Class<?> source, Class<?> target) {
        return source.isAssignableFrom(target);
    }

    @Override
    public void exit() {
        System.exit(1);
    }

    @Override
    protected void initComponent( IComponent component ) {
        this.initComponent(null, component);
    }

    protected void initComponent( Menu context, IComponent<JComponent> component ) {
        component.init();

        for ( IController controller : component.getControllers() ) {
            Dispatcher.get().addController( controller );
            this.getContext().getBean(IControllerInitializer.class).init(controller);
        }

        Menu menu = null;
        if ( component.doRenderMenu() ) {
            menu = this.createMenu(component);
            if ( context == null ) {
                UIRegistry.<MenuBar>get(UIConstants.System.MENUBAR).add( menu );
            } else {
                context.add( menu );
            }
        }

        for ( IComponent child : component.getChildren() ) {
            this.initComponent(menu, child);
        }
    }

    @Override
    protected void initWidget( UIConstants.Area area, IWidget widget ) {
        widget.init();

        JComponent component = UIRegistry.get(area);
        if ( component != null ) {
            widget.render(component);
        } else {
            log.info("Requested area (" + area.name() + ") does not registered within UIRegistry...");
        }
    }

    @Override
    public void start() throws ApplicationException {
        Dispatcher.get().addListener(UIEvents.Core.Init, new IEventHandler() {
            private static final long serialVersionUID = 2748988068351419365L;

            @Override
            public void handle(AppEvent type) {
                SwingApplication.this.context.setVisible(true);
                Dispatcher.get().forwardEvent( UIEvents.Core.Repaint );
            }
        });
    }
    
    protected Menu createMenu( IComponent<JComponent> component ) {
        Menu menu = new Menu();
        menu.setLabel( component.getTitle() );

        for ( final ComponentAction action : component.getActions() ) {
            if ( action instanceof ComponentAction.Empty ) {
                menu.addSeparator();
            } else {
                MenuItem item = new MenuItem( String.valueOf( action.getName() ) );
                item.addActionListener( new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        action.handle( new AppEvent(UIEvents.Core.System, e) );
                    }
                });

                menu.add( item );
            }
        }

        return menu;
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

    protected Thread.UncaughtExceptionHandler createUncaughtHandler() {
        return new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Dispatcher.get().forwardEvent( UIEvents.Core.Error, e );
            }
        };
    }

    protected AbstractMainWindow getMainWindow() {
        return this.context;
    }

}
