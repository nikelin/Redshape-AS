package com.redshape.plugins.update.updaters;

import com.redshape.plugins.info.IPluginInfo;
import com.redshape.plugins.update.PluginUpdaterException;
import com.redshape.plugins.update.sources.IPluginUpdateSource;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 4:34:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPluginUpdater {

    public boolean hasUpdates( IPluginUpdateSource source );

    public List<IPluginInfo> getUpdates( IPluginUpdateSource source );

    public void update( IPluginUpdateSource source ) throws PluginUpdaterException;

}
