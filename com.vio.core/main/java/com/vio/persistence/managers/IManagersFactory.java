package com.vio.persistence.managers;

import com.vio.config.readers.ConfigReaderException;
import com.vio.persistence.ProviderException;
import com.vio.persistence.entities.IEntity;

import javax.persistence.EntityManagerFactory;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 21, 2010
 * Time: 1:01:13 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IManagersFactory {

    public IManager getForEntity( IEntity entity ) throws ManagerException;

    public IManager getForEntity( Class<? extends IEntity> entity ) throws ManagerException;

    public EntityManagerFactory getEJBFactory() throws ConfigReaderException, SQLException, ProviderException;
                                       
    public <T extends Manager> T getManager( Class<T> clazz );

}
