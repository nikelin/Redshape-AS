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

import com.redshape.utils.IPackagesLoader;
import com.redshape.utils.config.ConfigException;
import com.redshape.persistence.migration.strategy.MigrationStrategy;
import com.redshape.persistence.migration.strategy.entities.Rollback;
import com.redshape.persistence.migration.strategy.entities.Update;
import com.redshape.utils.InterfacesFilter;
import com.redshape.utils.PackageLoaderException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 17, 2010
 * Time: 1:33:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class EntitiesMigrationManager extends AbstractMigrationManager {
    private static MigrationManager defaultInstance = new EntitiesMigrationManager();
    private static final Logger log = Logger.getLogger( MigrationManager.class );
    
    @Autowired( required = true )
    private IPackagesLoader packagesLoader;
    
    public static MigrationManager getDefault() {
        return defaultInstance;
    }

    public static void setDefault( MigrationManager manager ) {
        defaultInstance = manager;
    }

    public EntitiesMigrationManager() {
        this.setStrategy( Action.UPDATE, new Update(this) );
        this.setStrategy( Action.ROLLBACK, new Rollback(this) );
    }
    
    public void setPackagesLoader( IPackagesLoader loader ) {
    	this.packagesLoader = loader;
    }
    
    public IPackagesLoader getPackagesLoader() {
    	return this.packagesLoader;
    }

    @SuppressWarnings("unchecked")
	public void migrate( int from, int to ) throws MigrationException {
        MigrationStrategy strategy = this.getStrategy( this.getAction(from, to) );
        log.info("Processing migrations with strategy " + strategy.getClass().getName() + " from version " + from + " to " + to );

        try {
            strategy.execute( this.getMigrations(), from, to );
        } catch ( Throwable e ) {
            throw new MigrationException();
        }
    }

    protected Class<? extends Migrator>[] getMigrations() throws PackageLoaderException, ConfigException {
        return this.getPackagesLoader().<Migrator>getClasses( 
        			this.getConfig().get("database").get("migrations").attribute("package"),
        			new InterfacesFilter( 
    					new Class[] { Migrator.class } 
					) 
			);
    }
}
