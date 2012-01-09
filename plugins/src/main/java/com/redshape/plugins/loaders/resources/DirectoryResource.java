package com.redshape.plugins.loaders.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * Created by IntelliJ IDEA.
 * User: Nikelin
 * Date: 09.01.12
 * Time: 19:29
 * To change this template use File | Settings | File Templates.
 */
public class DirectoryResource implements IPluginResource {
    private File file;

    public DirectoryResource( File file ) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    @Override
    public URI getURI() {
        return this.file.toURI();
    }

    @Override
    public int getSize() {
        return this.file.list().length;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        throw new IOException("Operation not supported");
    }

    @Override
    public OutputStream getOuputStream() throws IOException {
        throw new IOException("Operation not supported");
    }

    @Override
    public boolean canWrite() {
        return this.file.canWrite();
    }

    @Override
    public boolean canRead() {
        return this.file.canRead();
    }
}
