package com.redshape.plugins.loaders;

import com.redshape.plugins.LoaderException;
import com.redshape.plugins.loaders.resources.IPluginResource;

import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/20/11
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ILoadersFacade {
    
    public IPluginResource load( URI path ) throws LoaderException;
    
}
