package com.vio.persistence.entities;

import com.vio.persistence.managers.IManager;
import com.vio.persistence.managers.Manager;
import com.vio.persistence.managers.ManagerException;
import com.vio.persistence.managers.ManagersFactory;

import javax.persistence.*;

import org.apache.log4j.*;

/**
 * Абстракция для представления объекта из хранилища постоянных
 * объектов
 *
 * @author nikelin
 */
@MappedSuperclass
@PersistenceContext( type = PersistenceContextType.EXTENDED )
public abstract class AbstractEntity<T extends IEntity> implements IEntity {
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

    public IManager getDAO() throws ManagerException {
        return ManagersFactory.getDefault().getForEntity(this);
    }

    public void save() throws ManagerException {
        this.getDAO().save( (T) this );
    }

    public void save( boolean doNestedSave ) throws ManagerException {
        this.getDAO().save( (T) this, doNestedSave );
    }

    public void remove() throws ManagerException {
        this.getDAO().remove( this);
    }

    public boolean isExists() throws ManagerException {
        return this.getDAO().isExists( this);
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

    public boolean inSameVersion( IEntity e ) {
        return e.getEntityLockVersion() != 0 && this.getEntityLockVersion().equals( e.getEntityLockVersion() );
    }

    public String getEntityName() throws ManagerException {
        return this.getDAO().getEntityName();
    }
}
