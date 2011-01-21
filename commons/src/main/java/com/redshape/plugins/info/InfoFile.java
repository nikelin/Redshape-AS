package com.redshape.plugins.info;

import java.io.File;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 6:20:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class InfoFile {
    private String extension;
    private Object source;

    public InfoFile( String extension, Object source ) {
        this.extension = extension;
        this.source = source;
    }

    public Object getSource() {
        return this.source;
    }

    public String getExtension() {
        return this.extension;
    }

    public boolean isStreamSource() {
        return InputStream.class.isAssignableFrom( this.source.getClass() );
    }

    public boolean isFileSource() {
        return File.class.isAssignableFrom( this.source.getClass() );
    }
}
