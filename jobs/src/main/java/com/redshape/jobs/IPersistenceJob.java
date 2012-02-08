package com.redshape.jobs;

import com.redshape.persistence.entities.IEntity;

/**
 * @package com.redshape.daemon.jobs
 * @user cyril
 * @date 6/21/11 11:12 PM
 */
public interface IPersistenceJob extends IEntity, IJob {

    public Integer getFailuresCount();

    public void increaseFailuresCount();

}
