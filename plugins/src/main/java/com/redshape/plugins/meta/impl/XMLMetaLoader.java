package com.redshape.plugins.meta.impl;

import com.redshape.plugins.LoaderException;
import com.redshape.plugins.meta.AbstractMetaLoader;
import com.redshape.plugins.meta.IPluginInfo;
import com.redshape.plugins.packagers.*;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.XMLConfig;
import com.redshape.utils.helpers.XMLHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.meta
 * @date 10/11/11 4:44 PM
 */
public class XMLMetaLoader extends AbstractMetaLoader {
    public static String META_FILE_PATH = "plugin-info.xml";

	@Autowired( required = true )
	private XMLHelper helper;

    public XMLMetaLoader() {
        this( new SimpleDateFormat("dd/MM/yyyy") );
    }

    public XMLMetaLoader(DateFormat format) {
        super(format);
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
            XMLConfig config = XMLConfig.build( this.getHelper(),
                new String( descriptor.getEntry(META_FILE_PATH).getData() ) );

			return this.buildPluginInfo(descriptor, config);
		} catch ( ConfigException e ) {
			throw new LoaderException("Corrupted manifest declaration", e);
		}
	}

    @Override
    public boolean isSupports(IPackageDescriptor descriptor) {
        return descriptor.hasEntry(META_FILE_PATH);
    }
}
