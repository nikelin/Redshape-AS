package com.redshape.jobs;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.dao.IDAO;
import com.redshape.persistence.dao.query.executors.IExecutionRequest;
import com.redshape.persistence.entities.IEntity;

/**
 * @package com.redshape.projectshost.persistence.dao
 * @user cyril
 * @date 6/21/11 10:14 PM
 */
public interface IJobsDAO<T extends IPersistenceJob> extends IDAO<T> {

    public IExecutionRequest<T> findByStatus( JobStatus status ) throws DAOException;

}
