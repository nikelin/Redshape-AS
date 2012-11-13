/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.plugins.loaders.impl;

import com.redshape.plugins.LoaderException;
import com.redshape.plugins.loaders.IPluginsLoader;
import com.redshape.plugins.loaders.resources.DirectoryResource;
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
            File file = this.getResourcesLoader().loadFile(path);
            if ( !file.exists() ) {
                throw new LoaderException("Plugin source not founded withing provided path");
            }

            if ( file.isDirectory() ) {
                return new DirectoryResource(file);
            }

            FileInputStream inputStream = new FileInputStream(file);
            FileOutputStream outputStream = new FileOutputStream(file);

            return new PluginResource( path, (int) file.length(), inputStream, outputStream  );
        } catch ( IOException e ) {
            throw new LoaderException( e.getMessage(), e );
        }
    }
}
