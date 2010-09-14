package com.redshape.persistence.interceptors;

import com.redshape.persistence.entities.IEntity;
import com.redshape.persistence.managers.ManagerException;

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
            return this.isTransient( (IEntity) entity);
        } catch ( Throwable e ) {
            return true;
        }
    }

    public Boolean isTransient( IEntity entity ) throws ManagerException {
        if ( entity.getId() != null ) {
            return false;
        }

        IEntity record = entity.getDAO().find( entity );
        if ( !entity.equals(record) ) {
            return true;
        }

        entity.setId( record.getId() );

        return false;
    }
}
