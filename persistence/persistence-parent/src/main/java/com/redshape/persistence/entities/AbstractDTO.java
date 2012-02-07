package com.redshape.persistence.entities;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.entities
 * @date 2/2/12 {11:56 AM}
 */
public abstract class AbstractDTO implements IDTO {
    private Long id;
    private Class<? extends IEntity> entityClass;

    protected AbstractDTO(Class<? extends IEntity> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean isDto() {
        return true;
    }

    @Override
    public Class<? extends IEntity> getEntityClass() {
        return this.entityClass;
    }
}
