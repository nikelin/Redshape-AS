package com.vio.persistence.entities;

import com.vio.persistence.managers.Manager;
import com.vio.persistence.managers.ManagerException;
import com.vio.search.ISearchable;

/**
 * Интерфейс для представления абстрактного объекта из хранилища постоянных
 * объектов
 *
 * @author nikelin
 */
public interface Entity extends ISearchable {

    public Integer getId();

    public void setId( Integer id );

    public Manager getDAO() throws ManagerException;

    public void save() throws ManagerException;

    public void remove() throws ManagerException;

    public boolean isExists() throws ManagerException;

    public void setEntityLockVersion( Integer version );

    public Integer getEntityLockVersion();

    public boolean inSameVersion( Entity object );

}
