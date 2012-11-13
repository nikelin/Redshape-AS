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

package com.redshape.plugins.loaders;

import com.redshape.plugins.loaders.compression.ICompressionSupport;
import com.redshape.plugins.loaders.impl.HttpPluginLoader;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/14/11
 * Time: 3:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class StandardPluginLoadersRegistry implements IPluginLoadersRegistry {
    private Map<String, IPluginsLoader> loaders = new HashMap<String, IPluginsLoader>();

    public StandardPluginLoadersRegistry() {
        super();

        this.registerLoader("http", new HttpPluginLoader() );
    }

    public void setLoaders(Map<String, IPluginsLoader> loaders) {
        this.loaders = loaders;
    }

    @Override
    public IPluginsLoader selectLoader(URI path) {
        return this.loaders.get( path.getScheme() );
    }

    @Override
    public void registerLoader(String scheme, IPluginsLoader loader) {
        this.loaders.put( scheme, loader );
    }
}
