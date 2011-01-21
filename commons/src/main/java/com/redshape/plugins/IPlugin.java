package com.redshape.plugins;

import com.redshape.renderer.Renderable;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Sep 30, 2010
 * Time: 3:14:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Renderable
public interface IPlugin  {
    String getSystemId();

    void setSystemId( String id );

    PluginInfo getInfo();

    void setInfo( PluginInfo info );

    void init() throws PluginInitException;

    void unload() throws PluginUnloadException;
}
