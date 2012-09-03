package com.redshape.plugins.starters;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nikelin
 * Date: 09.01.12
 * Time: 18:08
 * To change this template use File | Settings | File Templates.
 */
public interface IStartersRegistry {

    public void registerStarter( EngineType type, IStarterEngine engine );

    public EngineType[] getSupported();

    public IStarterEngine selectEngine( EngineType type );

}
