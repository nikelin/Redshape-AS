package com.vio.persistence.entities;

import com.vio.persistence.managers.IManager;
import com.vio.persistence.managers.Manager;
import com.vio.persistence.managers.ManagerException;
import com.vio.search.ISearchable;

/**
 * Интерфейс для представления абстрактного объекта из хранилища постоянных
 * объектов
 *
 * @author nikelin
 */
public interface IEntity extends ISearchable {

    public Integer getId();

    public void setId( Integer id );

    public IManager getDAO() throws ManagerException;

    public void save() throws ManagerException;

    public void remove() throws ManagerException;

    public boolean isExists() throws ManagerException;

    public Integer getEntityLockVersion();

}
