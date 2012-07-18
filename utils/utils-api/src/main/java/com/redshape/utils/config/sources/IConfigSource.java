package com.redshape.utils.config.sources;

import com.redshape.utils.config.ConfigException;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/11/11
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IConfigSource extends Serializable {

    public interface OnChangeCallback {

        public void onChanged();

    }

    public void setCallback( OnChangeCallback callback );

    public void markClean();

    public void reload();

    public boolean isChanged();

    public String read() throws ConfigException;
    
    public void write( String data ) throws ConfigException;
    
    public boolean isWritable();
    
    public boolean isReadable();
    
}
