package com.redshape.utils.config.sources;

import com.redshape.utils.config.ConfigException;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/11/11
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IConfigSource {
    
    public String read() throws ConfigException;
    
    public void write( String data ) throws ConfigException;
    
    public boolean isWritable();
    
    public boolean isReadable();
    
}
