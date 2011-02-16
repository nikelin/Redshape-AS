package com.redshape.migration;

import com.redshape.utils.config.IConfig;
import com.redshape.migration.strategy.MigrationStrategy;
import com.redshape.utils.ResourcesLoader;

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
