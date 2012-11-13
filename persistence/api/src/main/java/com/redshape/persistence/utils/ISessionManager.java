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
