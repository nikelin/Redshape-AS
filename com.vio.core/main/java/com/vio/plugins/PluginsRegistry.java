package com.vio.plugins;

import com.vio.plugins.info.InfoLoader;
import com.vio.plugins.info.InfoLoaderFactory;
import com.vio.plugins.loaders.PluginLoader;
import com.vio.plugins.loaders.PluginLoaderException;
import com.vio.plugins.sources.PluginSource;
import com.vio.plugins.sources.PluginSourcesFactory;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 4:53:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class PluginsRegistry {
    private static final Logger log = Logger.getLogger( PluginsRegistry.class);
    private static Map<String, String> loadedPaths = new HashMap<String, String>();
    private static Map<PluginInfo, Plugin> registry = new HashMap<PluginInfo, Plugin>();

    public static Plugin load( String path ) throws PluginLoaderException {
        return load( PluginSourcesFactory.getInstance().createSource(path) );
    }

    protected static Plugin load( PluginSource source ) throws PluginLoaderException {
        try {
            Plugin plugin = registry.get(source);
            if ( plugin != null ) {
                return plugin;
            }

            InfoLoader loader = InfoLoaderFactory.getLoader( source.getInfoFile() );
            if ( loader == null ) {
                throw new PluginLoaderException("Loader is null...");
            }

            PluginInfo pluginInfo = loader.getInfo();
            PluginLoader pluginLoader = pluginInfo.getLoader();
            try {
                plugin = pluginLoader.load( pluginInfo );

                plugin.init();
            } catch ( PluginInitException e ) {
                throw new PluginLoaderException("Plugin initializing exception!");
            }catch ( Throwable e ) {
                log.error( e.getMessage(), e );
                throw new PluginLoaderException("Cannot instantiate plugin Main-Class!");
            }

            loadedPaths.put( source.getPath(), pluginInfo.getSystemId() );
            registry.put( pluginInfo, plugin );

            return plugin;
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new PluginLoaderException();
        }
    }

    public static boolean isLoaded( String path ) {
        return loadedPaths.containsKey(path);
    }

    public static void unload( String path ) {
        if ( loadedPaths.get(path) != null ) {
            unload( getById( loadedPaths.get(path) ) );
        }
    }

    public static void unload( Plugin plugin ){
        try {
            plugin.unload();
        } catch ( PluginUnloadException e ) {
            log.error("Plugin unloading error!", e );
        }

        loadedPaths.remove( plugin.getSystemId() );
        registry.remove(plugin);
    }

    public static boolean isRegisteredId( String id ) {
        for ( PluginInfo info : registry.keySet() ) {
            if ( info.getSystemId().equals(id) ) {
                return true;
            }
        }

        return false;
    }

    public static Plugin getById( String id ) {
        Plugin result = null;
        for ( PluginInfo info : registry.keySet() ) {
            if ( info.getSystemId().equals(id) ) {
                result = registry.get(info);
                break;
            }
        }

        return result;
    }

    public static PluginInfo getPluginInfo( Plugin plugin ) {
        PluginInfo result = null;
        for ( PluginInfo info : registry.keySet() ) {
            if ( info.getSystemId().equals( plugin.getSystemId() ) ) {
                result = info;
                break;
            }
        }

        return result;
    }
    
    public static Plugin[] getPlugins(){
        return registry.values().toArray( new Plugin[registry.size()] );
    }

    public static String generateSystemId( PluginInfo info ) {
        return new Formatter()
                    .format("%s_%s", info.getName(), info.getVersion() )
                        .toString();
    }
    
}
