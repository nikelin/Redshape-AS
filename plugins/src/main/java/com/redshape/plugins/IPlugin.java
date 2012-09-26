package com.redshape.plugins;

/**
 * @author nikelin
 */
public interface IPlugin  {

    public void setAttribute( String name, Object value );
    
    public <V> V getAttribute( String name );

    void init() throws PluginInitException;

    void unload() throws PluginUnloadException;

}
