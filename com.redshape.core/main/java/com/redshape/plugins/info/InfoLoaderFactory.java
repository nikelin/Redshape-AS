package com.redshape.plugins.info;

import com.redshape.plugins.loaders.PluginLoaderException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 5:22:49 PM
 * To change this template use File | Settings | File Templates.
 */
public final class InfoLoaderFactory {
    private static Map< String, Class<? extends InfoLoader> > registry = new HashMap<String, Class<? extends InfoLoader> >();
   
    public static InfoLoader getLoader( InfoFile file ) throws PluginLoaderException {
        try {
            Class<? extends InfoLoader> loaderClazz = registry.get( file.getExtension() );
            if ( loaderClazz == null ) {
                return null;
            }

            return  loaderClazz.getConstructor(file.getClass()).newInstance(file);
        } catch ( Throwable e ) {
            throw new PluginLoaderException();
        }
    }

    public static Map<String, Class<? extends InfoLoader> > getLoaders() {
        return registry;
    }

    public static void addExtensionLoader( String extension, Class<? extends InfoLoader> clazz ) {
        registry.put( extension, clazz );
    }

}
