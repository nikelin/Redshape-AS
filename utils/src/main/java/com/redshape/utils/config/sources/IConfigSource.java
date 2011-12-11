package com.redshape.utils.config.sources;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/11/11
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IConfigSource {
    
    public Reader getReader() throws IOException;
    
    public Writer getWriter() throws IOException;
    
    public boolean isWritable();
    
    public boolean isReadable();
    
}
