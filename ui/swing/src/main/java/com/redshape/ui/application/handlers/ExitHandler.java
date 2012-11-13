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
 * Time: 0:54
 * To change this template use File | Settings | File Templates.
 */
public class ExitHandler implements IEventHandler {
	private static final long serialVersionUID = -4074001133004585194L;
	
	private IApplication context;

    public ExitHandler( IApplication application ) {
        this.context = application;
    }

    @Override
    public void handle(AppEvent event) {
        if ( JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(
                UIRegistry.<JFrame>get(UIConstants.System.WINDOW), "Are you really want to exit?") ) {
            System.out.println("Exiting...");

            this.context.exit();
        }
    }

}
