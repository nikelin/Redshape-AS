package com.redshape.plugins.loaders.resources;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/16/11
 * Time: 4:39 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPluginResource {

    public URI getURI();
    
    public int getSize();
    
    public InputStream getInputStream() throws IOException;
    
    public OutputStream getOuputStream() throws IOException;

    public boolean canWrite();

    public boolean canRead();
    
}
