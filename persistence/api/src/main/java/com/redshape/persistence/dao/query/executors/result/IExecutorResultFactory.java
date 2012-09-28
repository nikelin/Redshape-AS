package com.redshape.persistence.dao.query.executors.result;

import com.redshape.persistence.entities.IEntity;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.dao.query.executors.services
 * @date 2/6/12 {2:57 PM}
 */
public interface IExecutorResultFactory {

    public <T extends IEntity> IExecutorResult<T> wrapResult( IExecutorResult<T> input );
    
    public <T extends IEntity> IExecutorResult<T> createResult( Object input );

}
