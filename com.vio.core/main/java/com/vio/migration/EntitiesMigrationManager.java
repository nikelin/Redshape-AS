package com.vio.migration;

import com.vio.config.readers.ConfigReaderException;
import com.vio.migration.strategy.MigrationStrategy;
import com.vio.migration.strategy.entities.Rollback;
import com.vio.migration.strategy.entities.Update;
import com.vio.utils.InterfacesFilter;
import com.vio.utils.PackageLoaderException;
import com.vio.utils.Registry;
import org.apache.log4j.Logger;

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

    public void migrate( int from, int to ) throws MigrationException {
        MigrationStrategy strategy = this.getStrategy( this.getAction(from, to) );
        log.info("Processing migrations with strategy " + strategy.getClass().getName() + " from version " + from + " to " + to );

        try {
            strategy.execute( this.getMigrations(), from, to );
        } catch ( Throwable e ) {
            throw new MigrationException();
        }
    }

    protected Class<? extends Migrator>[] getMigrations() throws PackageLoaderException, ConfigReaderException {
        return Registry.getPackagesLoader().<Migrator>getClasses( Registry.getDatabaseConfig().getMigrationsPackage(), new InterfacesFilter( new Class[] { Migrator.class } ) );
    }
}
