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
