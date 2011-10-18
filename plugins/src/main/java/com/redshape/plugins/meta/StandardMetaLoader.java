package com.redshape.plugins.meta;

import com.redshape.plugins.LoaderException;
import com.redshape.plugins.meta.data.PluginInfo;
import com.redshape.plugins.packagers.IPackageDescriptor;
import com.redshape.utils.IResourcesLoader;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;
import com.redshape.utils.config.XMLConfig;
import com.redshape.utils.helpers.XMLHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.meta
 * @date 10/11/11 4:44 PM
 */
public class StandardMetaLoader implements IMetaLoader {

	@Autowired( required = true )
	private IResourcesLoader loader;

	@Autowired( required = true )
	private XMLHelper helper;

	public IResourcesLoader getLoader() {
		return loader;
	}

	public void setLoader(IResourcesLoader loader) {
		this.loader = loader;
	}

	public XMLHelper getHelper() {
		return helper;
	}

	public void setHelper(XMLHelper helper) {
		this.helper = helper;
	}

	@Override
	public IPluginInfo load(IPackageDescriptor descriptor) throws LoaderException {
		try {
			File file = this.getLoader().loadFile( descriptor.getMetaPath() );
			if ( file == null ) {
				throw new LoaderException("Unable to read plugin manifest file");
			}

			XMLConfig config = new XMLConfig( this.getHelper(), file );

			PluginInfo info = new PluginInfo( descriptor );
			info.setPublisher(this.parsePublisher(config));
			info.setArchVersion( this.parseArchVersion(config) );
			return info;
		} catch ( IOException e ) {
			throw new LoaderException("Unable to read plugin manifest file");
		} catch ( ConfigException e ) {
			throw new LoaderException("Corrupted manifest declaration");
		}
	}

	public IPublisherInfo parsePublisher( IConfig config ) {
		return null;
	}

	public String parseArchVersion( IConfig config ) {
		return null;
	}
}
