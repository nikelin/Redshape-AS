package com.redshape.persistence.entities;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.entities
 * @date 2/2/12 {11:55 AM}
 */
public interface IDTO extends IEntity {

    public Class<? extends IEntity> getEntityClass();
    
}
