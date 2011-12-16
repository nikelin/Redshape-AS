package com.redshape.plugins.loaders.compression;

import com.redshape.plugins.loaders.resources.IPluginResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/16/11
 * Time: 4:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class StandardCompressionSupport implements ICompressionSupport {

    @Override
    public InputStream decompressStream(InputStream stream) throws IOException {
        return null;  //to change body of implemented methods use file | settings | file templates.
    }

    @Override
    public OutputStream compressStream(OutputStream stream) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isCompressed(IPluginResource resource) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
