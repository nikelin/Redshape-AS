package com.vio.migration.strategy.entities;

import com.vio.migration.Action;
import com.vio.migration.MigrationException;
import com.vio.migration.MigrationManager;
import com.vio.migration.Migrator;

import java.util.Collection;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 26, 2010
 * Time: 1:11:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class Rollback extends AbstractEntitiesStrategy {

    public Rollback( MigrationManager manager ) {
        super(manager);
    }

    protected void processMigrators( Migrator[] migrators, int from, int to ) throws MigrationException {
        int delta = from - to;
        int version = from;

        Collection<Class<?>> processed = new Vector<Class<?>>();
        int activations = 0;
        while( activations++ < migrators.length && activations != delta ) {
            for ( Migrator migrator : migrators ) {
                if ( ( migrator.getVersion() >= version && !processed.contains( migrator.getClass() )  ) && !migrator.isReductant( Action.ROLLBACK ) ) {
                    processed.add( migrator.getClass() ); 
                    migrator.rollback();
                    break;
                }
            }

            version -= 1;
        }
    }

    public boolean isAffectable( int version, int from, int to ) {
        return from > to && version < from && version > to;
    }
}
