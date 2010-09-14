package com.redshape.applications.bootstrap.actions;

import com.redshape.applications.bootstrap.AbstractBootstrapAction;
import com.redshape.applications.bootstrap.Action;
import com.redshape.plugins.info.InfoLoaderFactory;
import com.redshape.plugins.info.XMLInfoLoader;
import com.redshape.plugins.sources.DirectoryPluginSource;
import com.redshape.plugins.sources.JarPluginSource;
import com.redshape.plugins.sources.PluginSourcesFactory;

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
