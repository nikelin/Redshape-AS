package com.redshape.plugins.meta;

import com.redshape.plugins.LoaderException;
import com.redshape.plugins.packagers.IPackageDescriptor;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.meta
 * @date 10/11/11 4:43 PM
 */
public interface IMetaLoader {

	public IPluginInfo load( IPackageDescriptor descriptor ) throws LoaderException;

}
