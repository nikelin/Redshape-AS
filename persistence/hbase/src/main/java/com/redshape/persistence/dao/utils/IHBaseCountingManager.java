package com.redshape.persistence.dao.utils;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.entities.IEntity;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.dao.utils
 * @date 1/24/12 {6:22 PM}
 */
public interface IHBaseCountingManager {

    public void increaseCount( Class<? extends IEntity> entity ) throws DAOException;

    public void increaseCount( Class<? extends IEntity> entity, Long count ) throws DAOException;

    public void decreaseCount( Class<? extends IEntity> entity ) throws DAOException;

    public void decreaseCount( Class<? extends IEntity> entity, Long count ) throws DAOException;

    public void reset( Class<? extends IEntity> entity ) throws DAOException;

    public Long count( Class<? extends IEntity> entity ) throws DAOException;

}
