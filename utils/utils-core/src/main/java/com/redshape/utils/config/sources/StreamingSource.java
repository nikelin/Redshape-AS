package com.redshape.utils.config.sources;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/11/11
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class StreamingSource implements IConfigSource {
    private InputStream input;
    private OutputStream output;

    public StreamingSource( InputStream input ) {
        this(input, null);
    }
    
    public StreamingSource( InputStream input, OutputStream output ) {
        this.input = input;
        this.output = output;
    }

    @Override
    public Reader getReader() throws IOException {
        if ( !this.isReadable() ) {
            throw new IllegalStateException("Write-only source");
        }

        return new InputStreamReader(this.input);
    }

    @Override
    public Writer getWriter() throws IOException {
        if ( !this.isWritable() ) {
            throw new IllegalStateException("Read-only source");
        }

        return new OutputStreamWriter(this.output);
    }

    @Override
    public boolean isWritable() {
        return this.output != null;
    }

    @Override
    public boolean isReadable() {
        return this.input != null;
    }
}
