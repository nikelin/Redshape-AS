package com.redshape.plugins.registry;

import com.redshape.plugins.IPlugin;
import com.redshape.plugins.info.IPluginInfo;
import com.redshape.plugins.loaders.PluginLoaderException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.registry
 * @date 9/19/11 8:08 PM
 */
public interface IPluginsRegistry {
	public IPlugin load(String path) throws PluginLoaderException;

	public void unload(String path) throws PluginLoaderException;

	public void unload(IPlugin plugin) throws PluginLoaderException;

	public boolean isLoaded(String path);

	public boolean isRegisteredId(String id);

	public IPlugin getById(String id);

	public IPluginInfo getPluginInfo(IPlugin plugin);

	public IPlugin[] getPlugins();

	public String generateSystemId(IPluginInfo info);
}
