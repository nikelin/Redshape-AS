package com.redshape.persistence.entities;

import org.apache.log4j.Logger;

import javax.persistence.*;

/**
 * @author nikelin
 */
@MappedSuperclass
@PersistenceContext( type = PersistenceContextType.EXTENDED )
public abstract class AbstractEntity<T extends IDTO> implements IEntity, IDtoCapable<T> {
    private static final long serialVersionUID = 4734062738612714789L;
    private static final Logger log = Logger.getLogger( AbstractEntity.class );

    @Id @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;

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
            return super.equals( o );
        }
    }

    public boolean equals( IEntity e ) {
        return this.getId() != null && e.getId() != null && this.getId().equals( e.getId() );
    }

    @Override
    public T toDTO() {
        return DtoUtils.toDTO( this );
    }

    @Override
    public boolean isDto() {
        return false;
    }
}
