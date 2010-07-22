package com.vio.config;

import com.vio.config.readers.ConfigReader;
import com.vio.config.readers.ConfigReaderException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 17, 2010
 * Time: 11:08:15 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IConfig {

    public void setReader( ConfigReader reader );

    public ConfigReader getReader();

    public List<String> getValuesList( String path ) throws ConfigReaderException;

    public String getValue( String path ) throws ConfigReaderException;

    public String getPath( String path ) throws ConfigReaderException;
}
