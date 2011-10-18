package com.redshape.plugins.loaders;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.loaders
 * @date 10/11/11 12:22 PM
 */
public interface IPluginLoadersRegistry {

	public IPluginsLoader selectLoader( String path );

}
