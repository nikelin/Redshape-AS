package com.vio.config.readers;

import java.io.File;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 14, 2010
 * Time: 10:44:12 AM
 * To change this template use File | Settings | File Templates.
 */
abstract public class AbstractReader implements ConfigReader {
    private File file;
    private InputStream inputStream;

    public AbstractReader( File file ) {
        this.file = file;
    }

    public AbstractReader( InputStream stream ) {
        this.inputStream = stream;
    }

    protected InputStream getInputStream() {
        return this.inputStream;
    }

    protected File getFile() {
        return this.file;
    }

}
