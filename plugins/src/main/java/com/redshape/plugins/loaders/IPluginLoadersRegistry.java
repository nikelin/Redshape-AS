package com.redshape.plugins.loaders;

import java.net.URI;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.loaders
 * @date 10/11/11 12:22 PM
 */
public interface IPluginLoadersRegistry {

    public void registerLoader( String scheme, IPluginsLoader loader );
    
	public IPluginsLoader selectLoader( URI path );

}
