package com.redshape.utils;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils
 * @date 8/13/11 12:27 PM
 */
public interface IPackagesLoader {

	public <T> Class<T>[] getClasses(String pkgName) throws PackageLoaderException;

	public <T> Class<T>[] getClasses(String pkgName, IFilter<Class<T>> filter) throws PackageLoaderException;

}
