package com.redshape.persistence.dao.query.executors.result;

import com.redshape.persistence.entities.IEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 1/23/12
 * Time: 3:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IExecutorResult<T extends IEntity> extends Serializable {
    
    public <Z> List<Z> getValuesList();
    
    public <Z> Z getSingleValue();
    
    public List<T> getResultsList();
    
    public T getSingleResult();
    
    public int count();
    
}
