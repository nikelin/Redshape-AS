package com.vio.plugins.update.updaters;

import com.vio.plugins.PluginInfo;
import com.vio.plugins.update.PluginUpdaterException;
import com.vio.plugins.update.sources.PluginUpdateSource;

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
