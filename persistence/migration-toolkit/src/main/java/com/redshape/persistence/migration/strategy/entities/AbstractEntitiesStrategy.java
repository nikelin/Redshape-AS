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

package com.redshape.persistence.migration.strategy.entities;

import com.redshape.persistence.migration.MigrationException;
import com.redshape.persistence.migration.MigrationManager;
import com.redshape.persistence.migration.Migrator;
import com.redshape.persistence.migration.strategy.MigrationStrategy;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 26, 2010
 * Time: 1:05:13 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractEntitiesStrategy implements MigrationStrategy<Class<? extends Migrator>[]> {
    private MigrationManager manager;
    private static final Logger log = Logger.getLogger( AbstractEntitiesStrategy.class );

    public AbstractEntitiesStrategy( MigrationManager manager ) {
        this.manager = manager;
    }

    public MigrationManager getManager() {
        return this.manager;
    }

    abstract protected void processMigrators( Migrator[] migrators, int from, int to ) throws MigrationException;

    public void execute( Class<? extends Migrator>[] migrations, int from, int to ) throws MigrationException {
        Migrator[] migrators = new Migrator[migrations.length];
        for ( int i = 0; i < migrations.length; i++ ) {
            try {
                Migrator migrator = this.createMigrator( migrations[i] );;
                if ( this.isAffectable( migrator.getVersion(), from, to ) ) {
                    migrators[i] = migrator;
                }
            } catch ( MigrationException e ) {
                log.error( e.getMessage(), e );    
            }
        }

        this.processMigrators( migrators, from, to );
    }

    protected Migrator createMigrator( Class<? extends Migrator> migrator ) throws MigrationException {
        try {
            return migrator.newInstance();
        } catch ( Throwable e ) {
            throw new MigrationException( e.getMessage() );
        }
    }
}
