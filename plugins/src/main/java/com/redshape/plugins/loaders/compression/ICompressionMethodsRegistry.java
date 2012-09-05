package com.redshape.plugins.loaders.compression;

import com.redshape.plugins.loaders.resources.IPluginResource;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/16/11
 * Time: 4:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ICompressionMethodsRegistry {
    
    public ICompressionSupport select( IPluginResource resource );
    
}
