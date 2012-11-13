/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.persistence.entities;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.entities
 * @date 2/2/12 {11:56 AM}
 */
public abstract class AbstractDTO implements IDTO {
    private Long id;
    private Class<? extends IEntity> entityClass;

    public AbstractDTO() {
        this(null);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractDTO)) return false;

        AbstractDTO that = (AbstractDTO) o;

        if (entityClass != null ? !entityClass.equals(that.entityClass) : that.entityClass != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (entityClass != null ? entityClass.hashCode() : 0);
        return result;
    }
}
