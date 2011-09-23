package com.redshape.plugins.loaders;

import com.redshape.plugins.IPlugin;
import com.redshape.plugins.info.IPluginInfo;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 29, 2010
 * Time: 5:28:06 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PluginLoader {

    public IPlugin load( IPluginInfo info ) throws PluginLoaderException;

}
