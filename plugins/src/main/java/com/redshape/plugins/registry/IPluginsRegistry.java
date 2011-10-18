package com.redshape.plugins.registry;

import com.redshape.plugins.meta.IPluginInfo;

import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.registry
 * @date 10/11/11 12:18 PM
 */
public interface IPluginsRegistry {

	public List<IPluginInfo> getPluginsList();

}
