package com.redshape.plugins.starters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Nikelin
 * Date: 09.01.12
 * Time: 18:14
 * To change this template use File | Settings | File Templates.
 */
public class StandardStartersRegistry implements IStartersRegistry {

    private Map<EngineType, IStarterEngine> engines = new HashMap<EngineType, IStarterEngine>();

    public StandardStartersRegistry(Map<EngineType, IStarterEngine> engines) {
        this.engines = engines;
    }

    @Override
    public void registerStarter(EngineType type, IStarterEngine engine) {
        this.engines.put(type, engine);
    }

    @Override
    public EngineType[] getSupported() {
        return this.engines.keySet().toArray( new EngineType[this.engines.size()] );
    }

    @Override
    public IStarterEngine selectEngine(EngineType type) {
        return this.engines.get(type);
    }
}
