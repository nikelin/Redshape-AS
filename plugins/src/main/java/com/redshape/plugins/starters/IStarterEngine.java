package com.redshape.plugins.starters;

import com.redshape.plugins.IPlugin;
import com.redshape.plugins.meta.IPluginInfo;

import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.starters
 * @date 10/11/11 4:50 PM
 */
public interface IStarterEngine {

	public IPlugin resolve( IPluginInfo plugin );

    public void start( IPlugin plugin );

    public void stop( IPlugin plugin );
    
}
