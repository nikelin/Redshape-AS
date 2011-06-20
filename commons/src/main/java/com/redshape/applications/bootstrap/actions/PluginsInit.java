package com.redshape.applications.bootstrap.actions;

import com.redshape.applications.bootstrap.AbstractBootstrapAction;
import com.redshape.plugins.info.InfoLoaderFactory;
import com.redshape.plugins.info.XMLInfoLoader;
import com.redshape.plugins.sources.DirectoryPluginSource;
import com.redshape.plugins.sources.JarPluginSource;
import com.redshape.plugins.sources.PluginSourcesFactory;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 22, 2010
 * Time: 2:41:01 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class PluginsInit extends AbstractBootstrapAction {
	public final static String ID = "Boot.Action.Plugins";

    public PluginsInit() {
        this.setId(ID);
    }

    public void process() {
        PluginSourcesFactory.getInstance().addArchiveSourceType("jar", JarPluginSource.class );
        PluginSourcesFactory.getInstance().addArchiveSourceType("directory", DirectoryPluginSource.class );
        InfoLoaderFactory.addExtensionLoader("xml", XMLInfoLoader.class);
    }

}
