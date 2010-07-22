package com.vio.plugins;

import com.vio.render.Renderable;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 4:25:57 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Plugin implements Renderable {
    private String systemId;
    private PluginInfo info;

    public Plugin( String systemId, PluginInfo info ) {
        this.systemId = systemId;
        this.info = info;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId( String id ) {
        this.systemId = id;
    }

    public PluginInfo getInfo() {
        return this.info;
    }

    public void setInfo( PluginInfo info ) {
        this.info = info;
    }

    abstract public void init() throws PluginInitException;

    abstract public void unload() throws PluginUnloadException;
}
