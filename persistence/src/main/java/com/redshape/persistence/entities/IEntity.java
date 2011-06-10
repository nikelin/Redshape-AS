package com.redshape.persistence.entities;

import java.io.Serializable;

import com.redshape.persistence.dao.DAOException;

/**
 * @author nikelin
 */
public interface IEntity extends Serializable {

    public Long getId();

    public void setId( Long id );
    
    public void onDelete() throws DAOException;
    
}
