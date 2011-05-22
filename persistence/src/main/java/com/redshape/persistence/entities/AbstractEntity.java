package com.redshape.persistence.entities;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.Collection;

import javax.persistence.*;

import org.apache.log4j.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author nikelin
 */
@MappedSuperclass
@PersistenceContext( type = PersistenceContextType.EXTENDED )
public abstract class AbstractEntity implements IEntity {
    private static final long serialVersionUID = 4734062738612714789L;
    private static final Logger log = Logger.getLogger( AbstractEntity.class );

    @Id @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;

    @Transient
    private Integer entityLockVersion = 0;

    public Integer getEntityLockVersion() {
        return this.entityLockVersion;
    }

    public void setEntityLockVersion( Integer version ) {
        this.entityLockVersion = version;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId( Long id ) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        if ( this.getId() != null ) {
            return this.getId().hashCode();
        }

        return super.hashCode();
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

    protected boolean isCollectionMember( Field field ) {
    	return field.getAnnotation( ManyToOne.class ) != null
    			|| field.getAnnotation( OneToOne.class ) != null
    				|| field.getAnnotation( ManyToMany.class ) != null;
    }
    
}
