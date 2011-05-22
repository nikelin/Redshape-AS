package com.redshape.persistence.entities;

import com.redshape.persistence.entities.IEntity;
import com.redshape.tasks.ITask;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.tasks
 * @date May 3, 2010
 */
public interface IPersistentTask extends ITask, IEntity {
    public static Integer DEFAULT_EXECUTION_DELAY = ITask.DEFAULT_EXECUTION_DELAY;
    public static Integer DEFAULT_MAX_EXECUTION_DELAY = ITask.DEFAULT_MAX_EXECUTION_DELAY;

    public Integer getExecutionFails();    

    public void markFail();

    public Long getLastExecutionTime();

    public void setLastExecutionTime( Long timestamp );

    public boolean isCompleted();    

}
