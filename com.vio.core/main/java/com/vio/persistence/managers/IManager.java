package com.vio.persistence.managers;

import com.vio.persistence.entities.Entity;

import java.util.List;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.persistence.managers
 * @date Mar 19, 2010
 */
public interface IManager {

    public <T extends Entity> T save( T object ) throws ManagerException;

    public void remove() throws ManagerException;

    public boolean isExists( Entity object ) throws ManagerException;

    public <T extends Entity> List<T> findBy( String name, Object value ) throws ManagerException;
}
