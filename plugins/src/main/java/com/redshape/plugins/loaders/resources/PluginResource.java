package com.redshape.plugins.loaders.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/20/11
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class PluginResource implements IPluginResource {
    private URI uri;
    private int size;
    private InputStream inputStream;
    private OutputStream outputStream;
    private boolean readable;
    private boolean writable;
    
    public PluginResource( URI uri, int size, InputStream stream ) {
        this(uri, size, stream, null);
    }
    
    public PluginResource( URI uri, int size, OutputStream stream ) {
        this(uri, size, null, stream);
    }
    
    public PluginResource( URI uri, int size, InputStream inputStream, OutputStream outputStream ) {
        super();

        this.size = size;
        this.uri = uri;
        this.inputStream = inputStream;
        this.readable = this.inputStream != null;
        this.outputStream = outputStream;
        this.writable = this.outputStream != null;
    }
    
    @Override
    public URI getURI() {
        return this.uri;
    }

    @Override
    public int getSize() {
        return this.size;
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
        return this.writable;
    }

    @Override
    public boolean canRead() {
        return this.readable;
    }
}
