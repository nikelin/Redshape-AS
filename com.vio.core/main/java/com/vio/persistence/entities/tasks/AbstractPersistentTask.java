package com.vio.persistence.entities.tasks;

import com.vio.persistence.entities.AbstractEntity;
import com.vio.tasks.IPersistentTask;
import com.vio.tasks.ITask;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import com.vio.persistence.entities.IEntity;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.persistence.entities.tasks
 * @date Apr 5, 2010
 */
@MappedSuperclass
public abstract class AbstractPersistentTask<T extends IEntity> extends AbstractEntity<T> implements IPersistentTask {

    @Basic
    @Column( name = "time" )
    private Long createdTime;

    @Basic
    private Long updatedTime;

    @Basic
    private Priority priority = Priority.NORMAL;

    @Basic
    private Integer executionFails = 0;

    @Basic
    private Long lastExecutionTime = new Long(0);

    @Enumerated
    private ITask.State state = ITask.State.WAITING;

    public Long getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(Long time)
    {
       this.createdTime = time;
    }

    public void setPriority( Priority priority ) {
        this.priority = priority;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public Long getUpdatedTime() {
        return this.updatedTime;
    }

    public Integer getExecutionDelay() {
        return IPersistentTask.DEFAULT_EXECUTION_DELAY;
    }

    public Integer getMaxExecutionDelay() {
        return IPersistentTask.DEFAULT_MAX_EXECUTION_DELAY;
    }

    public Integer getExecutionFails() {
        return this.executionFails;
    }

    public void markFail() {
        this.state = ITask.State.ERROR;
        this.executionFails += 1;
    }

    public Long getLastExecutionTime() {
        return this.lastExecutionTime;
    }

    public void setLastExecutionTime( Long timestamp ) {
        this.lastExecutionTime = timestamp;
    }

    protected void markCompleted() {
        this.state = ITask.State.COMPLETED;
    }

    public boolean isCompleted() {
        return this.state == ITask.State.COMPLETED;
    }

    public boolean isPersistent() {
        return true;
    }

}
