package com.redshape.migration;

import com.redshape.utils.config.ConfigException;
import com.redshape.migration.strategy.MigrationStrategy;
import com.redshape.migration.strategy.fixtures.Rollback;
import com.redshape.migration.strategy.fixtures.Update;
import com.redshape.utils.BeansLoader;
import com.redshape.utils.ObjectsLoader;

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
