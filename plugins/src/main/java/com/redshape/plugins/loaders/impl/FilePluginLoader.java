package com.redshape.plugins.loaders.impl;

import com.redshape.plugins.LoaderException;
import com.redshape.plugins.loaders.IPluginsLoader;
import com.redshape.plugins.loaders.resources.IPluginResource;
import com.redshape.plugins.loaders.resources.PluginResource;
import com.redshape.utils.IResourcesLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/20/11
 * Time: 4:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class FilePluginLoader implements IPluginsLoader {

    @Autowired( required = true )
    private IResourcesLoader resourcesLoader;

    public IResourcesLoader getResourcesLoader() {
        return resourcesLoader;
    }

    public void setResourcesLoader(IResourcesLoader resourcesLoader) {
        this.resourcesLoader = resourcesLoader;
    }

    @Override
    public IPluginResource load(URI path) throws LoaderException {
        try {
            File file = new File(path);
            FileInputStream inputStream = new FileInputStream(file);
            FileOutputStream outputStream = new FileOutputStream(file);

            return new PluginResource( path, (int) file.length(), inputStream, outputStream  );
        } catch ( IOException e ) {
            throw new LoaderException( e.getMessage(), e );
        }
    }
}
