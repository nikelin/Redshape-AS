package com.redshape.plugins.packagers;

import java.io.File;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.packagers
 * @date 10/11/11 1:09 PM
 */
public interface IPackageSupport {

	/**
	 * Unpack target path (if required) and return package structure
	 * descriptor.
	 *
	 * @param path
	 * @return
	 */
	public IPackageDescriptor unpack( String path );

	/**
	 * Unpack target path (if required) and return package structure
	 * descriptor.
	 *
	 * @param path
	 * @return
	 */
	public IPackageDescriptor unpack( File file ) throws PackagerException;

	/**
	 * Check that a given packaging type supported by a current provider
	 * @param type
	 * @return
	 */
	public boolean isSupported( PackagingType type );

}
