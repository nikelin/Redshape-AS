package com.redshape.persistence.entities;

import java.io.Serializable;

/**
 * @author nikelin
 */
public interface IEntity extends Serializable {

    public Long getId();

    public void setId( Long id );
    
}
