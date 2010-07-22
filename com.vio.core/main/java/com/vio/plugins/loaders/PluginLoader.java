package com.vio.plugins.loaders;

import com.vio.plugins.Plugin;
import com.vio.plugins.PluginInfo;
import com.vio.plugins.PluginUnloadException;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 29, 2010
 * Time: 5:28:06 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PluginLoader {

    public Plugin load( PluginInfo info ) throws PluginLoaderException;

}
