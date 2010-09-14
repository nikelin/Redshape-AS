package com.redshape.config;

import com.redshape.config.cache.IConfigCacheProvider;

/**
 * XMLConfig handler
 *
 * @author nikelin
 */
public interface IConfig {

    public void setCacheProvider( IConfigCacheProvider provider );

    /**
     * Get child context
     * @param name
     * @return
     */
    public IConfig get( String name ) throws ConfigException;

    /**
     * 
     */
    public <T extends IConfig> T[] childs();

    public String[] list();

    /**
     * Read values list
     * 
     * @return
     */
    public String[] list( String name );

    public String name();

    /**
     * Read and return all names of the children
     *
     * @return String[]
     */
    public String[] names();

    /**
     * Get named attribute value
     * @param name
     * @return
     */
    public String attribute( String name );

    /**
     * Get value of current node
     * @return
     */
    public String value();

}
