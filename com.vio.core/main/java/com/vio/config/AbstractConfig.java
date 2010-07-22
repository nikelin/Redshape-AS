package com.vio.config;

import com.vio.config.readers.ConfigReader;
import com.vio.config.readers.ConfigReaderException;
import com.vio.config.readers.XMLConfigReader;
import com.vio.utils.Registry;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 17, 2010
 * Time: 11:11:36 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractConfig implements IConfig {
    private ConfigReader reader;

    public AbstractConfig( String path ) throws ConfigReaderException, IOException {
        this( Registry.getResourcesLoader().loadFile(path) );
    }

    public AbstractConfig( File file ) throws ConfigReaderException {
        this.reader = new XMLConfigReader( file );
        this.reader.initialize();
    }

    public void setReader( ConfigReader reader ) {
        this.reader = reader;
    }

    public ConfigReader getReader() {
        return this.reader;
    }

    public String getValue( String path ) throws ConfigReaderException {
        return this.getReader().read( path );
    }

    public List<String> getValuesList( String path ) throws ConfigReaderException {
        return this.getReader().readList(path);
    }

    public String getPath( String name ) throws ConfigReaderException {
        return this.getReader().read("//paths/path[@name='" + name + "']/@path");
    }

}
