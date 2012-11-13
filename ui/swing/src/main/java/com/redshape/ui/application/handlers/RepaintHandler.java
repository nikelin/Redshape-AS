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

package com.redshape.ui.application.handlers;

import com.redshape.ui.application.IApplication;
import com.redshape.ui.application.events.AppEvent;
import com.redshape.ui.application.events.IEventHandler;
import com.redshape.ui.utils.UIConstants;
import com.redshape.ui.utils.UIRegistry;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 23.05.11
 * Time: 0:51
 * To change this template use File | Settings | File Templates.
 */
public class RepaintHandler implements IEventHandler {
	private static final long serialVersionUID = -359502273946842268L;
	
	@SuppressWarnings("unused")
	private IApplication context;

    public RepaintHandler( IApplication context ) {
        this.context = context;
    }

    @Override
    public void handle(AppEvent event) {
        if ( event.getArg(0) != null && ( event.getArg(0) instanceof JComponent ) ) {
            JComponent component = event.getArg(0);
            component.revalidate();
            component.repaint();
        } else if ( event.getArg(0) != null && ( event.getArg(0) instanceof UIConstants.Attribute ) ) {
            Object registryObject = UIRegistry.get(event.<UIConstants.Attribute>getArg(0));
            // TODO: java.awt.Component needs to be repainted to...
            if ( registryObject instanceof JComponent ) {
                final JComponent component = ( (JComponent) registryObject );
                component.revalidate();
                component.repaint();
            }
        } else {
            for ( UIConstants.Area area : UIConstants.Area.values() ) {
                JComponent component = UIRegistry.get( area );
                if ( component == null ) {
                    continue;
                }

                component.revalidate();
                component.repaint();
            }

            JFrame window = UIRegistry.<JFrame>get(UIConstants.System.WINDOW);
            window.invalidate();
            window.repaint();
        }
    }
}