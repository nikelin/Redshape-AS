package com.vio.migration.strategy.fixtures;

import com.vio.migration.MigrationException;
import com.vio.migration.strategy.MigrationStrategy;
import com.vio.persistence.entities.IEntity;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 26, 2010
 * Time: 3:38:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Rollback implements MigrationStrategy<Iterable<IEntity>> {

    public void execute( Iterable<IEntity> objects, int from, int to) throws MigrationException {
        try {
            for ( IEntity entity : objects ) {
                if ( entity.getDAO().isExists( entity ) ) {
                    entity.getDAO().remove( entity );
                }
            }
        } catch ( Throwable e ) {
            throw new MigrationException();
        }
    }

    public boolean isAffectable( int version, int from, int to ) {
        return from > to && version < from && version > to;
    }

}
