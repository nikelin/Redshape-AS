package com.redshape.persistence.migration.strategy.fixtures;

import com.redshape.persistence.migration.MigrationException;
import com.redshape.persistence.migration.strategy.MigrationStrategy;

import com.redshape.persistence.entities.IEntity;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 26, 2010
 * Time: 3:38:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Update implements MigrationStrategy<Iterable<IEntity>> {
    private static final Logger log = Logger.getLogger( Update.class );

    // @FIXME: due to DAO refactoring
    public void execute( Iterable<IEntity> objects, int from, int to ) throws MigrationException {
        try {
            for ( IEntity entity : objects ) {
                   log.info("Saving entity " + entity.getClass().getName() + "...");
//                   entity.getDAO().save(entity, true);
            }
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new MigrationException();
        }
    }

    public boolean isAffectable( int version, int from, int to ) {
        return from < to && version > from && version < to; 
    }

}
