package com.redshape.persistence.dao.query.executors.result;

import com.redshape.persistence.entities.IEntity;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.dao.query.executors.result
 * @date 2/6/12 {3:03 PM}
 */
public class StandardExecutorResultFactory implements IExecutorResultFactory {

    @Override
    public <T extends IEntity> IExecutorResult<T> wrapResult(IExecutorResult<T> input) {
        return new DTOResult<T>( input.getResultsList() );
    }

    @Override
    public <T extends IEntity> IExecutorResult<T> createResult(Object input) {
        return new ExecutorResult<T>(input);
    }

}
