package com.vio.applications.bootstrap.actions;

import com.vio.applications.bootstrap.AbstractBootstrapAction;
import com.vio.applications.bootstrap.Action;
import com.vio.plugins.info.InfoLoaderFactory;
import com.vio.plugins.info.XMLInfoLoader;
import com.vio.plugins.sources.DirectoryPluginSource;
import com.vio.plugins.sources.JarPluginSource;
import com.vio.plugins.sources.PluginSourcesFactory;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 22, 2010
 * Time: 2:41:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class PluginsInit extends AbstractBootstrapAction {
    public PluginsInit() {
        this.setId(Action.PLUGINS_ID);
    }

    public void process() {
        PluginSourcesFactory.getInstance().addArchiveSourceType("jar", JarPluginSource.class );
        PluginSourcesFactory.getInstance().addArchiveSourceType("directory", DirectoryPluginSource.class );
        InfoLoaderFactory.addExtensionLoader("xml", XMLInfoLoader.class);
    }

}
