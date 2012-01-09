package com.redshape.plugins.packagers;

import com.redshape.plugins.loaders.resources.IPluginResource;

import java.io.File;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.packagers
 * @date 10/11/11 1:09 PM
 */
public interface IPackagesRegistry {

	public PackagingType detectType( IPluginResource resource );

	public IPackageSupport getSupport( PackagingType type );

	public void registerProvider( PackagingType type, IPackageSupport provider );

}
