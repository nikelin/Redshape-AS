package com.redshape.utils;

import java.net.URI;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils
 * @date 8/13/11 12:27 PM
 */
public interface IPackagesLoader {

    public interface ResourcesHandler {

        public Class<?> handle( String className ) throws ClassNotFoundException;

        public Class<?> handle( String className, URI[] uris ) throws ClassNotFoundException;

    }

    public void setDefaultResourcesHandler( ResourcesHandler handler );

    public void registerResourcesHandler( String pattern, ResourcesHandler handler );

	public <T> Class<T>[] getClasses(String pkgName) throws PackageLoaderException;

	public <T> Class<T>[] getClasses(String pkgName, IFilter<Class<T>> filter) throws PackageLoaderException;

}
