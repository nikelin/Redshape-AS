package com.redshape.plugins.loaders.compression;

import com.redshape.plugins.loaders.resources.IPluginResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/16/11
 * Time: 4:41 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ICompressionSupport {

    public InputStream decompressStream( InputStream stream ) throws IOException;
    
    public OutputStream compressStream( OutputStream stream ) throws IOException;

    public boolean isCompressed( IPluginResource resource );

}
