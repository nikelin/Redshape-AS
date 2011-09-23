package com.redshape.plugins.info.loaders;

import com.redshape.plugins.info.IPluginInfo;
import com.redshape.plugins.loaders.PluginLoaderException;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 5:11:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IInfoLoader {

    public IPluginInfo getInfo() throws PluginLoaderException;
    
}
