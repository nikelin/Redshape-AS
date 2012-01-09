package com.redshape.plugins.packagers;

import com.redshape.plugins.loaders.resources.IPluginResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.packagers
 * @date 10/11/11 1:09 PM
 */
public interface IPackageSupport {

    public IPackageDescriptor processResource( PackagingType type, IPluginResource resource) throws PackagerException;

	/**
	 * Check that a given packaging type supported by a current provider
	 * @param type
	 * @return
	 */
	public boolean isSupported( PackagingType type );

}
