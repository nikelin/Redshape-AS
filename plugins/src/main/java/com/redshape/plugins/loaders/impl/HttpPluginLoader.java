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
import com.redshape.plugins.loaders.resources.IPluginResource;
import com.redshape.plugins.loaders.resources.PluginResource;
import com.redshape.utils.Constants;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/20/11
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpPluginLoader implements IPluginsLoader {

    @Override
    public IPluginResource load(URI path) throws LoaderException {
        try {
            HttpURLConnection connection = (HttpURLConnection) path.toURL().openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout( Constants.TIME_SECOND * 30 );
            connection.setDoInput(true);
            connection.setDoOutput(false);

            return new PluginResource( path, connection.getContentLength(),
                    connection.getInputStream(), connection.getOutputStream() );
        } catch ( MalformedURLException e ) {
            throw new LoaderException( "Invalid loading path", e );
        } catch ( IOException e ) {
            throw new LoaderException( "Source interaction failed", e );
        }
    }

}
