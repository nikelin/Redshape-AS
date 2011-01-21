package com.redshape.plugins;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 4:25:57 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractPlugin implements IPlugin {
    private String systemId;
    private PluginInfo info;

    public AbstractPlugin( String systemId, PluginInfo info ) {
        this.systemId = systemId;
        this.info = info;
    }

    @Override
    public String getSystemId() {
        return this.systemId;
    }

    @Override
    public void setSystemId( String id ) {
        this.systemId = id;
    }

    @Override
    public PluginInfo getInfo() {
        return this.info;
    }

    @Override
    public void setInfo( PluginInfo info ) {
        this.info = info;
    }

}
