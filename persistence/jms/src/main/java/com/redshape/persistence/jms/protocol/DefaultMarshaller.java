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

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.protocol
 * @date 1/25/12 {4:52 PM}
 */
public class DefaultMarshaller implements IQueryMarshaller {
    
    @Override
    public Message marshal( Message result, IQuery source) throws ProtocolException {
        try {
            if ( !( result instanceof ObjectMessage ) ) {
                throw new ProtocolException("Only object message type is" +
                        " supported by default implementation");
            }

            ( (ObjectMessage) result).setObject( source );

            return result;
        } catch ( JMSException e ) {
            throw new ProtocolException("Query marshalling failed", e );
        }
    }

    @Override
    public Message marshal( Message result, IExecutorResult<?> source) throws ProtocolException {
        try {
            if ( !( result instanceof ObjectMessage ) ) {
                throw new ProtocolException("Only object message type is" +
                        " supported by default implementation");
            }

            ( (ObjectMessage) result ).setObject( source );

            return result;
        } catch ( JMSException e ) {
            throw new ProtocolException( e.getMessage(), e );
        }
    }

    @Override
    public <T extends IEntity> IExecutorResult<T> unmarshalResult( Message source)
            throws ProtocolException {
        try {
            if ( !( source instanceof ObjectMessage ) ) {
                throw new ProtocolException("Only object message type is" +
                        " supported by default implementation");
            }
            
            return (IExecutorResult<T>) ( (ObjectMessage) source ).getObject();
        } catch ( JMSException e ) {
            throw new ProtocolException( e.getMessage(), e );
        }
    }
    
    @Override
    public IQuery unmarshalQuery(IQueryBuilder builder, Message source) throws ProtocolException {
        try {
            if ( !( source instanceof ObjectMessage ) ) {
                throw new ProtocolException("Only object message type is" +
                        " supported by default implementation");
            }

            return (IQuery) ( (ObjectMessage) source).getObject();
        } catch ( JMSException e ) {
            throw new ProtocolException( e.getMessage(), e );
        }
    }
}
