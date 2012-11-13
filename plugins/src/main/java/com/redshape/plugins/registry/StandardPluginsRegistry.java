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

package com.redshape.plugins.registry;

import com.redshape.plugins.IPlugin;
import com.redshape.plugins.meta.IPluginInfo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Nikelin
 * Date: 14.01.12
 * Time: 17:27
 * To change this template use File | Settings | File Templates.
 */
public class StandardPluginsRegistry implements IPluginsRegistry {
    private Map<IPluginInfo, IPlugin> registry = new HashMap<IPluginInfo, IPlugin>();
    
    @Override
    public void registerPlugin(IPluginInfo info, IPlugin plugin) {
        this.registry.put(info, plugin);
    }

    @Override
    public IPluginInfo findInfo(IPlugin plugin) {
        for ( Map.Entry<IPluginInfo, IPlugin> entry : this.registry.entrySet() ) {
            if ( entry.getValue() == plugin ) {
                return entry.getKey();
            }
        }

        return null;
    }

    @Override
    public Collection<IPlugin> list() {
        return this.registry.values();
    }
}
