package com.redshape.renderer.json;

import com.redshape.renderer.AbstractRenderersFactory;
import com.redshape.utils.IPackagesLoader;
import com.redshape.utils.IResourcesLoader;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;

public class JSONRenderersFactory extends AbstractRenderersFactory {

    public JSONRenderersFactory( IConfig config, 
    						     IResourcesLoader resourcesLoader, 
    						     IPackagesLoader packagesLoader )
    	throws ConfigException {
        super( JSONRenderersFactory.class, config, packagesLoader, resourcesLoader );
    }

    public String getFactoryId() {
        return "json";
    }

	
}