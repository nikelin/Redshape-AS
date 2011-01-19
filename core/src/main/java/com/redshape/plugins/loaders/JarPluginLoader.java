package com.redshape.plugins.loaders;

import com.redshape.plugins.IPlugin;
import com.redshape.plugins.PluginInfo;
import com.redshape.plugins.PluginsRegistry;

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

    public IPlugin load( PluginInfo info ) throws PluginLoaderException {
        try {
            URLClassLoader loader = new URLClassLoader( new URL[] { new URL("jar", info.getSystemPath() + "!/",  info.getMainClass() ) });

            Class<?> pluginClazz = loader.loadClass( info.getMainClass() );
            if ( !IPlugin.class.isAssignableFrom( pluginClazz ) ) {
                throw new PluginLoaderException("Wrong plugin Main-Class type! Must do extending of com.redshape.plugins.Plugin type.");
            }

            String systemId = PluginsRegistry.generateSystemId(info);

            info.setSystemId( systemId );

            return (IPlugin) pluginClazz.getConstructor( String.class, PluginInfo.class )
                                       .newInstance( systemId, info );
        } catch ( Throwable e ) {
            throw new PluginLoaderException();
        }
    }

}
