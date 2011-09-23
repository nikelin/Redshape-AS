package com.redshape.plugins.loaders;

import com.redshape.plugins.IPlugin;
import com.redshape.plugins.info.IPluginInfo;
import com.redshape.plugins.info.PluginInfo;
import com.redshape.plugins.registry.IPluginsRegistry;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 8:44:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class DirectoryPluginLoader implements PluginLoader {    
    private static final Logger log = Logger.getLogger( DirectoryPluginLoader.class );

	@Autowired( required = true )
	private IPluginsRegistry registry;

	public IPluginsRegistry getRegistry() {
		return registry;
	}

	public void setRegistry(IPluginsRegistry registry) {
		this.registry = registry;
	}

	public IPlugin load( IPluginInfo info ) throws PluginLoaderException {
        log.info("In directory plugin loader...");

        try {
            info.setSystemId( this.getRegistry().generateSystemId(info) );

			/**
			 * Change to secured classloader
			 */
            return  (IPlugin) ClassLoader.getSystemClassLoader()
										.loadClass( info.getMainClass() )
										.getConstructor( String.class, PluginInfo.class )
										.newInstance( info.getSystemId(), info );
        } catch ( Throwable e ) {
            log.error("Plugin not exists or loading impossible", e );
            throw new PluginLoaderException();
        }
    }
}
