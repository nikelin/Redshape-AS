package com.redshape.plugins.loaders;

import com.redshape.plugins.IPlugin;
import com.redshape.plugins.info.IPluginInfo;
import com.redshape.plugins.info.PluginInfo;
import com.redshape.plugins.registry.IPluginsRegistry;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.plugins.loaders
 * @date Apr 20, 2010
 */
public class JarPluginLoader implements PluginLoader {

	@Autowired( required = true )
	private IPluginsRegistry registry;

	public IPluginsRegistry getRegistry() {
		return registry;
	}

	public void setRegistry(IPluginsRegistry registry) {
		this.registry = registry;
	}

	public IPlugin load( IPluginInfo info ) throws PluginLoaderException {
        try {
            URLClassLoader loader = new URLClassLoader( new URL[] { new URL("jar", info.getSystemPath() + "!/",  info.getMainClass() ) });

            Class<?> pluginClazz = loader.loadClass( info.getMainClass() );
            if ( !IPlugin.class.isAssignableFrom( pluginClazz ) ) {
                throw new PluginLoaderException("Wrong plugin Main-Class type! Must do extending of com.redshape.plugins.Plugin type.");
            }

            String systemId = this.getRegistry().generateSystemId(info);

            info.setSystemId( systemId );

            return (IPlugin) pluginClazz.getConstructor( String.class, PluginInfo.class )
                                       .newInstance( systemId, info );
        } catch ( Throwable e ) {
            throw new PluginLoaderException();
        }
    }

}
