package com.vio.plugins.loaders;

import com.vio.plugins.Plugin;
import com.vio.plugins.PluginInfo;
import com.vio.plugins.PluginsRegistry;
import com.vio.utils.Registry;

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

    public Plugin load( PluginInfo info ) throws PluginLoaderException {
        try {
            URLClassLoader loader = new URLClassLoader( new URL[] { new URL("jar", info.getSystemPath() + "!/",  info.getMainClass() ) });

            Class<?> pluginClazz = loader.loadClass( info.getMainClass() );
            if ( !Plugin.class.isAssignableFrom( pluginClazz ) ) {
                throw new PluginLoaderException("Wrong plugin Main-Class type! Must do extending of com.vio.plugins.Plugin type.");
            }

            String systemId = PluginsRegistry.generateSystemId(info);

            info.setSystemId( systemId );

            return (Plugin) pluginClazz.getConstructor( String.class, PluginInfo.class )
                                       .newInstance( systemId, info );
        } catch ( Throwable e ) {
            throw new PluginLoaderException();
        }
    }

}
