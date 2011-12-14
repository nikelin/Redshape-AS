package com.redshape.plugins.loaders;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/14/11
 * Time: 3:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class StandardPluginLoadersRegistry implements IPluginLoadersRegistry {
    private Map<String, IPluginsLoader> loaders = new HashMap<String, IPluginsLoader>();
    
    @Override
    public IPluginsLoader selectLoader(URI path) {
        return this.loaders.get( path.getScheme() );
    }

    @Override
    public void registerLoader(String scheme, IPluginsLoader loader) {
        this.loaders.put( scheme, loader );
    }
}
