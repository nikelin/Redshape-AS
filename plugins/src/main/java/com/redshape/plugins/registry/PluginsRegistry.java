package com.redshape.plugins.registry;

import com.redshape.plugins.*;
import com.redshape.plugins.info.loaders.IInfoLoader;
import com.redshape.plugins.info.IPluginInfo;
import com.redshape.plugins.info.loaders.InfoLoaderFactory;
import com.redshape.plugins.loaders.PluginLoader;
import com.redshape.plugins.loaders.PluginLoaderException;
import com.redshape.plugins.sources.PluginSource;
import com.redshape.plugins.sources.PluginSourcesFactory;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 4:53:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class PluginsRegistry implements IPluginsRegistry {
    private static final Logger log = Logger.getLogger( PluginsRegistry.class);
    private Map<String, String> loadedPaths = new HashMap<String, String>();
    private Map<IPlugin, IPluginInfo> registry = new HashMap<IPlugin, IPluginInfo>();

    @Override
	public IPlugin load(String path) throws PluginLoaderException {
        return this.load( PluginSourcesFactory.getInstance().createSource(path) );
    }

	/**
	 * @todo add check
	 * @param source
	 * @return
	 * @throws PluginLoaderException
	 */
    protected IPlugin load( PluginSource source ) throws PluginLoaderException {
        try {
            IInfoLoader loader = InfoLoaderFactory.getLoader(source.getInfoFile());
            if ( loader == null ) {
                throw new PluginLoaderException("Loader is null...");
            }

			IPlugin plugin = null;
            IPluginInfo pluginInfo = loader.getInfo();
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
            registry.put( plugin, pluginInfo );

            return plugin;
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new PluginLoaderException();
        }
    }

    @Override
	public boolean isLoaded(String path) {
        return loadedPaths.containsKey(path);
    }

    @Override
	public void unload(String path) {
        if ( loadedPaths.get(path) != null ) {
            unload( getById( loadedPaths.get(path) ) );
        }
    }

    @Override
	public void unload(IPlugin plugin){
        try {
            plugin.unload();
        } catch ( PluginUnloadException e ) {
            log.error("Plugin unloading error!", e );
        }

        loadedPaths.remove( this.getPluginInfo(plugin).getSystemId() );
        registry.remove(plugin);
    }

    @Override
	public boolean isRegisteredId(String id) {
        for ( IPluginInfo info : registry.values() ) {
            if ( info.getSystemId().equals(id) ) {
                return true;
            }
        }

        return false;
    }

    @Override
	public IPlugin getById(String id) {
        for ( IPlugin plugin : this.registry.keySet() ) {
			IPluginInfo info = this.registry.get(plugin);
            if ( info.getSystemId().equals(id) ) {
				return plugin;
            }
        }

        return null;
    }

    @Override
	public IPluginInfo getPluginInfo(IPlugin plugin) {
		return this.registry.get(plugin);
    }
    
    @Override
	public IPlugin[] getPlugins(){
        return this.registry.keySet().toArray( new IPlugin[ this.registry.size() ] );
    }

    @Override
	/**
	 * Add hash-part
	 */
	public String generateSystemId(IPluginInfo info) {
        return new Formatter()
                    .format("%s_%s", info.getName(), info.getVersion() )
					.toString();
    }
    
}
