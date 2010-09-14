package com.redshape.plugins.loaders;

import com.redshape.plugins.Plugin;
import com.redshape.plugins.PluginInfo;

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
