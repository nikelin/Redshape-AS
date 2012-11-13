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

package com.redshape.persistence.jms.protocol;

import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.dao.query.IQueryBuilder;
import com.redshape.persistence.dao.query.executors.result.IExecutorResult;
import com.redshape.persistence.entities.IEntity;

import javax.jms.Message;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.dao.query.protocol
 * @date 1/25/12 {3:28 PM}
 */
public interface IQueryMarshaller {

    /**
     *
     * @param result
     * @param source
     * @return
     * @throws ProtocolException
     */
    public Message marshal( Message result, IQuery source ) throws ProtocolException;

    /**
     *
     * @param result
     * @param source
     * @return
     * @throws ProtocolException
     */
    public Message marshal( Message result, IExecutorResult<?> source ) throws ProtocolException;

    /**
     * Process response message and try to construct IExecutorResult based
     * on it's fields.
     *
     * @param result
     * @param source
     * @param <T>
     * @return
     * @throws ProtocolException
     */
    public <T extends IEntity> IExecutorResult<T> unmarshalResult( Message source ) throws ProtocolException;

    /**
     *
     * @param builder
     * @param source
     * @return
     * @throws ProtocolException
     */
    public IQuery unmarshalQuery( IQueryBuilder builder, Message source ) throws ProtocolException;

}
