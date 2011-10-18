package com.redshape.plugins.loaders;

import com.redshape.plugins.LoaderException;
import com.redshape.plugins.meta.IMetaLoader;
import com.redshape.plugins.packagers.*;
import com.redshape.utils.IFilter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.loaders
 * @date 10/11/11 12:22 PM
 */
public class ListLoader implements IPluginsLoader {
	private final static Logger log = Logger.getLogger(ListLoader.class);

	@Autowired( required = true )
	private IMetaLoader metaLoader;

	@Autowired( required = true )
	private IPackagesRegistry packagedRegistry;

	@Autowired( required = true )
	private IPluginLoadersRegistry loadersRegistry;

	private String contextRoot;
	private IFilter<String> loadingFilter;

	public ListLoader( String contextRoot ) {
		this(contextRoot, null );
	}

	public ListLoader( String contextRoot, IFilter<String> loadingFilter ) {
		this.contextRoot = contextRoot;
	}

	public IMetaLoader getMetaLoader() {
		return metaLoader;
	}

	public void setMetaLoader(IMetaLoader metaLoader) {
		this.metaLoader = metaLoader;
	}

	public IPluginLoadersRegistry getLoadersRegistry() {
		return loadersRegistry;
	}

	public void setLoadersRegistry(IPluginLoadersRegistry loadersRegistry) {
		this.loadersRegistry = loadersRegistry;
	}

	public IPackagesRegistry getPackagedRegistry() {
		return packagedRegistry;
	}

	public void setPackagedRegistry(IPackagesRegistry packagedRegistry) {
		this.packagedRegistry = packagedRegistry;
	}

	@Override
	public void load() throws LoaderException {
		File file = new File(this.contextRoot);
		if ( !file.exists() ) {
			throw new LoaderException("Plugins root context not exists or inaccessible");
		}

		if ( !file.isDirectory() ) {
			throw new LoaderException("List loader context root must be a directory");
		}

		for ( File pluginEntry : file.listFiles() ) {
			if ( null != this.loadingFilter
				&& !this.loadingFilter.filter( pluginEntry.getAbsolutePath() ) ) {
				continue;
			}

			try {
				this.load( pluginEntry );
			} catch ( LoaderException e ) {
				log.error( String.format("Unable to load plugin: %s", pluginEntry.getName()), e );
			}
		}
	}

	protected void load( File pluginEntry ) throws LoaderException {
		PackagingType packageType = this.getPackagedRegistry().detectType(pluginEntry);
		if ( packageType.equals( PackagingType.UNKNOWN ) ) {
			throw new LoaderException("Unknown plugin entry type");
		}

		IPackageSupport support = this.getPackagedRegistry().getSupport(packageType);
		if ( support == null ) {
			throw new LoaderException("Unsupported packaging type");
		}

		IPackageDescriptor descriptor = null;
		try {
			descriptor = support.unpack(pluginEntry);
		} catch ( PackagerException e ) {
			throw new LoaderException("Packager unable to proceed unpack for a given subject", e );
		}


	}
}
