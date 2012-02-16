package com.redshape.utils.config.sources;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/11/11
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileSource implements IConfigSource {
    private File file;

    public FileSource( File file ) {
        this.file = file;
    }

    @Override
    public Reader getReader() throws IOException {
        return new FileReader(this.file);
    }

    @Override
    public Writer getWriter() throws IOException {
        return new FileWriter(this.file);
    }

    @Override
    public boolean isWritable() {
        return this.file.canWrite();
    }

    @Override
    public boolean isReadable() {
        return this.file.canRead();
    }
}
