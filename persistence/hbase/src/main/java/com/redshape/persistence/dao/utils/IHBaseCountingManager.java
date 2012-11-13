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
