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

package com.redshape.ui.gwt;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.redshape.ui.application.AbstractApplication;
import com.redshape.ui.application.ApplicationException;
import com.redshape.ui.application.IBeansProvider;
import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.components.IComponent;
import com.redshape.ui.components.actions.ComponentAction;
import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;
import com.redshape.ui.views.widgets.IWidget;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.ui.gwt
 * @date 2/8/12 {1:19 PM}
 */
public class GWTApplication extends AbstractApplication {

    public GWTApplication(IBeansProvider applicationContext)
            throws ApplicationException {
        super(applicationContext);
    }

    @Override
    public void exit() {

    }

    @Override
    protected void initInterceptor(IConfig node) throws ConfigException, ApplicationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected boolean isAssignableFrom(Class<?> source, Class<?> target) {
        return source.equals(target);
    }

    @Override
    protected void initComponent(IComponent<?> component) {
        component.init();

        if ( component.doRenderMenu() ) {
            this.renderComponentMenu(component, UIRegistry.<MenuBar>get(UIConstants.Area.MENU) );
        }
    }
    
    protected void renderComponentMenu( IComponent<?> component ) {
        this.renderComponentMenu(component, null);
    }
    
    protected void renderComponentMenu( IComponent<?> component, MenuBar menu ) {
        MenuBar componentMenu = new MenuBar( menu != null );
        for ( final ComponentAction action : component.getActions() ) {
            componentMenu.addItem(new MenuItem(action.getName(), new Command() {
                @Override
                public void execute() {
                    action.handle(new AppEvent());
                }
            }));
        }
        
        for ( IComponent childComponent : component.getChildren() ) {
            this.renderComponentMenu( childComponent, componentMenu );
        }
        
        if ( menu != null ) {
            menu.addItem( component.getTitle(), componentMenu );
        } else {
            RootPanel.get().add( componentMenu );
        }
    }

    @Override
    protected void initWidget(UIConstants.Area area, IWidget<?> widget) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
