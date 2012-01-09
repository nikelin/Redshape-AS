package com.redshape.projecthost.statisticsGatherer;

import com.redshape.plugins.IPlugin;
import com.redshape.plugins.PluginInitException;
import com.redshape.plugins.PluginUnloadException;

import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.System;

class Starter implements IPlugin {
    @Override
    public void setAttribute(String name, Object value) {
        
    }

    @Override
    public <V> V getAttribute(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void init() throws PluginInitException {
        System.out.println("Plugin initialized!");
    }

    @Override
    public void unload() throws PluginUnloadException {
        System.out.println("Plugin uninitiliazed!");
    }
}