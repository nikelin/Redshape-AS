package com.redshape.persistence.entities;

import com.redshape.persistence.dao.IManager;
import com.redshape.persistence.dao.ManagerException;

import javax.persistence.*;

import org.apache.log4j.*;

/**
 * @author nikelin
 */
@MappedSuperclass
@PersistenceContext( type = PersistenceContextType.EXTENDED )
public abstract class AbstractEntity implements IEntity {
    private static final Logger log = Logger.getLogger( AbstractEntity.class );

    @Id @GeneratedValue( strategy = GenerationType.AUTO )
    private Integer id;

    @Transient
    private Integer entityLockVersion = 0;


    public Integer getEntityLockVersion() {
        return this.entityLockVersion;
    }

    public void setEntityLockVersion( Integer version ) {
        this.entityLockVersion = version;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId( Integer id ) {
        this.id = id;
    }

    @Override
    public boolean equals( Object o ) {
        if ( o == null ) {
            return false;
        }

        try {
            return (Boolean) this.getClass().getMethod("equals", o.getClass() ).invoke( this, o );
        } catch ( Throwable e ) {
            log.info( e.getMessage() );
            return super.equals( o );
        }
    }

    public boolean equals( IEntity e ) {
        return this.getId() != null && e.getId() != null && this.getId().equals( e.getId() );
    }

}
