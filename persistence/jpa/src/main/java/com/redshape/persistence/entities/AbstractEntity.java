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

import org.apache.log4j.Logger;

import javax.persistence.*;

/**
 * @author nikelin
 */
@MappedSuperclass
@PersistenceContext( type = PersistenceContextType.EXTENDED )
public abstract class AbstractEntity<T extends IDTO> implements IEntity, IDtoCapable<T> {
    private static final long serialVersionUID = 4734062738612714789L;
    private static final Logger log = Logger.getLogger( AbstractEntity.class );

    @Id @GeneratedValue( strategy = GenerationType.AUTO )
    private Long id;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId( Long id ) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractEntity)) {
            return false;
        }

        AbstractEntity that = (AbstractEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }

        return true;
    }

    @Override
    public T toDTO() {
        return DtoUtils.toDTO( this );
    }

    @Override
    public boolean isDto() {
        return false;
    }
}
