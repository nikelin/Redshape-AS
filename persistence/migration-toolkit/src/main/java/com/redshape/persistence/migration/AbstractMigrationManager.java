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

package com.redshape.persistence.migration;

import com.redshape.persistence.migration.strategy.MigrationStrategy;
import com.redshape.utils.ResourcesLoader;
import com.redshape.utils.config.IConfig;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 10:57:14 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractMigrationManager implements MigrationManager {
    private Map<Action, MigrationStrategy> strategies = new HashMap<Action, MigrationStrategy>();

    @Autowired( required = true )
    private ResourcesLoader loader;
    
    @Autowired( required = true )
    private IConfig config;
    
    public void setConfig( IConfig config ) {
    	this.config = config;
    }
    
    public IConfig getConfig() {
    	return this.config;
    }
    
    public void setResourcesLoader( ResourcesLoader loader ) {
    	this.loader = loader;
    }
    
    public ResourcesLoader getResourcesLoader() {
    	return this.loader;
    }
    
    public void setStrategy( Action action, MigrationStrategy strategy ) {
        this.strategies.put( action, strategy );
    }

    public MigrationStrategy getStrategy( Action action ) {
        return this.strategies.get(action);
    }

    public Action getAction( int from, int to ) {
        if ( from > to ) {
            return Action.ROLLBACK;
        } else {
            return Action.UPDATE;
        }
    }
}
