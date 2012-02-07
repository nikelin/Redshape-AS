package com.redshape.persistence.utils;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.entities.IEntity;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.utils
 * @date 2/7/12 {2:33 PM}
 */
public interface ISessionManager {

    public void open() throws DAOException;

    public <T extends IEntity> T refresh( IEntity object );

    public void close() throws DAOException;

}
