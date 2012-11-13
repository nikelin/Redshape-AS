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

package com.redshape.ui.windows;

import javax.swing.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/9/11
 * Time: 2:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultMainWindow extends AbstractMainWindow {

    public DefaultMainWindow() {
        this(null);
    }

    public DefaultMainWindow( String title ) {
        this(title, null);
    }

    public DefaultMainWindow( String title, Dimension size ) {
        super( title, size );
    }

    @Override
    protected void configUI() {}

    @Override
    protected JPanel createEastPanel() {
        return new JPanel();
    }

    @Override
    protected JPanel createWestPanel() {
        return new JPanel();
    }

    @Override
    protected JPanel createNorthPanel() {
        return new JPanel();
    }

    @Override
    protected JPanel createCenterPanel() {
        JPanel panel = new JPanel();
        panel.setMinimumSize( new Dimension(400, 500) );
        return panel;
    }

    @Override
    protected MenuBar createMenu() {
        return new MenuBar();
    }

    @Override
    protected JPanel createBottom() {
        JPanel panel = new JPanel();
        panel.setBorder( BorderFactory.createEtchedBorder() );
        panel.setBackground( Color.GRAY );
        return panel;
    }
}
