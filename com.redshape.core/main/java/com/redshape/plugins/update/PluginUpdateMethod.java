package com.redshape.plugins.update;

import com.redshape.plugins.update.sources.PluginUpdateSource;
import com.redshape.plugins.update.updaters.PluginUpdater;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 4:33:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class PluginUpdateMethod {
    private PluginUpdater updater;
    private PluginUpdateSource source;

    public PluginUpdater getUpdater() {
        return this.updater;
    }

    public PluginUpdateSource getSource() {
        return this.source;
    }
}
