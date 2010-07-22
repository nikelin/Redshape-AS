package com.vio.persistence.managers;

import com.vio.persistence.entities.EntityException;

/**
 * @author nikelin
 */
public class ManagerException extends EntityException  {

    public ManagerException( String message ) {
        super(message);
    }

    public ManagerException() {
        super();
    }
}