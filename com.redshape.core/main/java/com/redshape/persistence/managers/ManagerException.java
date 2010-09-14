package com.redshape.persistence.managers;

import com.redshape.persistence.entities.EntityException;

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