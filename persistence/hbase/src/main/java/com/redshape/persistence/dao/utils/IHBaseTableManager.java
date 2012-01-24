package com.redshape.persistence.dao.utils;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.entities.IEntity;
import org.apache.hadoop.hbase.client.HTable;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.dao.utils
 * @date 1/24/12 {6:14 PM}
 */
public interface IHBaseTableManager {

    public void setup() throws DAOException;

    public void disable( HTable table ) throws DAOException;
    
    public void delete( HTable table ) throws DAOException;
    
    public HTable forName( String name ) throws DAOException;
    
    public HTable forEntity( Class<? extends IEntity> entityClazz ) throws DAOException;
    
}
