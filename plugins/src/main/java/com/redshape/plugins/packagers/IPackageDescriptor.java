package com.redshape.plugins.packagers;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.packagers
 * @date 10/11/11 1:13 PM
 */
public interface IPackageDescriptor {

	public String getRootPath();

	public String getMetaPath();

	public String getSourcesPath();

	public PackagingType getType();

}
