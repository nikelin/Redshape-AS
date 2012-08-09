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
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractEntity)) {
            return false;
        }

        AbstractEntity that = (AbstractEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }

        return true;
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
