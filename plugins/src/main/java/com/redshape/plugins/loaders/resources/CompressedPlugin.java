package com.redshape.plugins.loaders.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/20/11
 * Time: 4:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class CompressedPlugin implements IPluginResource {
    private IPluginResource resource;
    private InputStream inputStream;
    private OutputStream outputStream;
    
    public CompressedPlugin( IPluginResource resource, InputStream compressedInput, OutputStream compressedOutput ) {
        super();

        this.resource = resource;
        this.inputStream = compressedInput;
        this.outputStream = compressedOutput;
    }

    @Override
    public URI getURI() {
        return this.resource.getURI();
    }

    @Override
    public int getSize() {
        return this.resource.getSize();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return this.inputStream;
    }

    @Override
    public OutputStream getOuputStream() throws IOException {
        return this.outputStream;
    }

    @Override
    public boolean canWrite() {
        return this.resource.canWrite();
    }

    @Override
    public boolean canRead() {
        return this.resource.canRead();
    }
}
