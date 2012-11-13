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


import com.redshape.utils.config.ConfigException;
import com.redshape.persistence.migration.strategy.MigrationStrategy;
import com.redshape.persistence.migration.strategy.fixtures.Rollback;
import com.redshape.persistence.migration.strategy.fixtures.Update;
import com.redshape.utils.serializing.beans.BeansLoader;
import com.redshape.utils.serializing.ObjectsLoader;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 26, 2010
 * Time: 2:29:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataMigrationManager extends AbstractMigrationManager {
    public static Logger log = Logger.getLogger( DataMigrationManager.class );
    private static MigrationManager defaultInstance = new DataMigrationManager();
    private ObjectsLoader loader = new BeansLoader();

    public static MigrationManager getDefault() {
        return defaultInstance;
    }

    public static void setDefault( MigrationManager instance ) {
        defaultInstance = instance;
    }

    public DataMigrationManager() {
        this.setStrategy( Action.UPDATE, new Update() );
        this.setStrategy( Action.ROLLBACK, new Rollback() );
    }

    public ObjectsLoader getLoader() {
        return this.loader;
    }

    public void setLoader( ObjectsLoader loader ) {
        this.loader = loader;
    }

    public void migrate( int from, int to ) throws MigrationException {
        try {
            MigrationStrategy strategy = this.getStrategy( this.getAction( from, to ) );
            log.info("Data migration strategy is: " + strategy.getClass().getCanonicalName() );

            File fixturesDir = this.getResourcesLoader().loadFile( this.getConfig().get("database").get("fixturesPath").value() );

            for ( String fixturesPart : fixturesDir.list() ) {
                int fixtureVersion;
                try {
                    fixtureVersion = Integer.parseInt(fixturesPart);
                } catch ( NumberFormatException e ) {
                    log.info("Wrong fixtures directory: " + fixturesPart );
                    continue;
                }

                if ( !strategy.isAffectable( fixtureVersion, from, to ) ) {
                    log.info("Stratregy is not affectable for version: " + fixtureVersion + " in context of migration from " + from + " to " + to );
                    continue;
                }

                File fixturesPartDir = new File( fixturesDir.getAbsolutePath() + "/" + fixturesPart );
                for ( String fixtureFile :  fixturesPartDir.list() ) {
                    try {
                        strategy.execute( this.getLoader().loadObjects( fixturesPartDir.getAbsolutePath() + "/" + fixtureFile ), from, to );
                    } catch ( Throwable e ) {
                        log.error( e.getMessage(), e );
                    }
                }
            }
        } catch ( IOException e ) {
            log.info( e.getMessage(), e );
            throw new MigrationException();
        } catch ( ConfigException e ) {
            throw new MigrationException();
        }
    }

}
