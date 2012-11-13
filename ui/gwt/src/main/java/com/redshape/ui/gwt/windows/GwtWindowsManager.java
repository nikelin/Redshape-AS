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

package com.redshape.ui.gwt.windows;

import com.google.gwt.user.client.ui.Panel;
import com.redshape.ui.windows.IWindowsManager;
import com.redshape.utils.IFilter;

import java.util.Collection;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.ui.gwt.windows
 * @date 2/9/12 {7:53 PM}
 */
public class GwtWindowsManager implements IWindowsManager<Panel> {

    @Override
    public <T extends Panel> T get(Class<T> clazz) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T extends Panel> T open(Class<T> clazz) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void open(Panel window) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void close(Panel window) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void close(Class<? extends Panel> windowClazz) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void destory(Panel window) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<Panel> getOpened() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<Panel> getClosed() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void closeAll() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<Panel> filter(IFilter<Panel> panelIFilter) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isRegistered(Panel window) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<Panel> list() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Panel getFocusedWindow() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
