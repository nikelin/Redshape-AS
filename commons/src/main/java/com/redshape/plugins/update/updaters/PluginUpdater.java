package com.redshape.plugins.update.updaters;

import com.redshape.plugins.PluginInfo;
import com.redshape.plugins.update.PluginUpdaterException;
import com.redshape.plugins.update.sources.PluginUpdateSource;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 4:34:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PluginUpdater {

    public boolean hasUpdates( PluginUpdateSource source );

    public List<PluginInfo> getUpdates( PluginUpdateSource source );

    public void update( PluginUpdateSource source ) throws PluginUpdaterException;

}
