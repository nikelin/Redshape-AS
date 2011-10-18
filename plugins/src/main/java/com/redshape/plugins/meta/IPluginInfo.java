package com.redshape.plugins.meta;

import com.redshape.plugins.packagers.IPackageDescriptor;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.meta
 * @date 10/11/11 12:19 PM
 */
public interface IPluginInfo {

	/**
	 * Return version of Plugins API under which
	 * current plugin based on.
	 * @return
	 */
	public String getArchVersion();

	public IPublisherInfo getPublisher();

	public IPackageDescriptor getPackageDescriptor();

}
