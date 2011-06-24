package com.redshape.daemon.jobs;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.dao.IDAO;
import com.redshape.persistence.entities.IEntity;

import java.util.List;

/**
 * @package com.redshape.projectshost.persistence.dao
 * @user cyril
 * @date 6/21/11 10:14 PM
 */
public interface IJobsDAO<T extends IJob & IEntity> extends IDAO<T> {

    public List<T> findByStatus( JobStatus status ) throws DAOException;

    public List<T> findByStatus(JobStatus status, int offset, int limit) throws DAOException;

}
