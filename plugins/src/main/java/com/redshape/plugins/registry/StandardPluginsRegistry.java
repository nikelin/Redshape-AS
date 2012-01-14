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
