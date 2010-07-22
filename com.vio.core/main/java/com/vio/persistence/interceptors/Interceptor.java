package com.vio.persistence.interceptors;

import com.vio.persistence.entities.Entity;
import com.vio.persistence.managers.ManagerException;

import org.hibernate.EmptyInterceptor;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.persistence
 * @date Mar 19, 2010
 */
public class Interceptor extends EmptyInterceptor {
    private static final Logger log = Logger.getLogger( Interceptor.class );
    private List<org.hibernate.Interceptor> interceptors = new ArrayList<org.hibernate.Interceptor>();

    public Boolean isTransient( Object entity ) {
        try {
            return this.isTransient( (Entity) entity);
        } catch ( Throwable e ) {
            return true;
        }
    }

    public Boolean isTransient( Entity entity ) throws ManagerException {
        if ( entity.getId() != null ) {
            return false;
        }

        Entity record = entity.getDAO().find( entity );
        if ( !entity.equals(record) ) {
            return true;
        }

        entity.setId( record.getId() );

        return false;
    }
}
