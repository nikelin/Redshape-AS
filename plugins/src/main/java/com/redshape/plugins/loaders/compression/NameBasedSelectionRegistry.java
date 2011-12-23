package com.redshape.plugins.loaders.compression;

import com.redshape.plugins.loaders.resources.IPluginResource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/20/11
 * Time: 4:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class NameBasedSelectionRegistry implements ICompressionMethodsRegistry {
    private Map<String, ICompressionSupport> engines = new HashMap<String, ICompressionSupport>();

    @Override
    public ICompressionSupport select(IPluginResource resource) {
        String[] pathParts = resource.getURI().getPath().split("\\/");
        if ( pathParts.length < 1 ) {
            return null;
        }
        
        String[] nameParts = pathParts[pathParts.length-1].split("\\.");
        if ( nameParts.length != 2 ) {
            return null;
        }
        
        return this.engines.get( nameParts[1] );
    }
}
