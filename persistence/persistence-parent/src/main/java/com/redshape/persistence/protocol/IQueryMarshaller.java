package com.redshape.persistence.protocol;

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
