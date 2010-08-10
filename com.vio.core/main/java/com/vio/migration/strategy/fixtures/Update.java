package com.vio.migration.strategy.fixtures;

import com.vio.migration.MigrationException;
import com.vio.migration.strategy.MigrationStrategy;
import com.vio.persistence.entities.IEntity;
import com.vio.persistence.managers.ManagerException;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 26, 2010
 * Time: 3:38:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Update implements MigrationStrategy<Iterable<IEntity>> {
    private static final Logger log = Logger.getLogger( Update.class );

    public void execute( Iterable<IEntity> objects, int from, int to ) throws MigrationException {
        try {
            for ( IEntity entity : objects ) {
               try {
                   log.info("Saving entity " + entity.getClass().getName() + "...");
                   entity.getDAO().save(entity, true);
               } catch ( ManagerException e ) {
                   if ( e.getCause().getClass().equals( ConstraintViolationException.class ) ) {
                        log.error("Duplicate entry... Ignoring.");
                   }
               }
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
