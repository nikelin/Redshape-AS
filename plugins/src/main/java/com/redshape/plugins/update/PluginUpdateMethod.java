package com.redshape.plugins.update;

import com.redshape.plugins.update.sources.IPluginUpdateSource;
import com.redshape.plugins.update.updaters.IPluginUpdater;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 4:33:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class PluginUpdateMethod {
    private IPluginUpdater updater;
    private IPluginUpdateSource source;

    public IPluginUpdater getUpdater() {
        return this.updater;
    }

    public IPluginUpdateSource getSource() {
        return this.source;
    }
}
