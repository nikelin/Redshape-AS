/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
