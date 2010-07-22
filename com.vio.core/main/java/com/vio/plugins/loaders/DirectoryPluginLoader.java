package com.vio.plugins.loaders;

import com.vio.plugins.Plugin;
import com.vio.plugins.PluginInfo;

import com.vio.plugins.PluginUnloadException;
import com.vio.plugins.PluginsRegistry;
import org.apache.log4j.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 8:44:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class DirectoryPluginLoader implements PluginLoader {    
    private static final Logger log = Logger.getLogger( DirectoryPluginLoader.class );

    public Plugin load( PluginInfo info ) throws PluginLoaderException {
        log.info("In directory plugin loader...");

        try {
            String systemId = PluginsRegistry.generateSystemId(info);

            info.setSystemId( systemId );
            
            return  (Plugin) ClassLoader.getSystemClassLoader()
                                                .loadClass( info.getMainClass() )
                                                .getConstructor( String.class, PluginInfo.class )
                                                .newInstance( systemId, info );
        } catch ( Throwable e ) {
            log.error("Plugin not exists or loading impossible", e );
            throw new PluginLoaderException();
        }
    }
}
