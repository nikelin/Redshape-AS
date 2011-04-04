package com.redshape.persistence.entities;

import com.redshape.persistence.entities.AbstractEntity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

import com.redshape.tasks.ITask;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.persistence.entities.tasks
 * @date Apr 5, 2010
 */
@MappedSuperclass
public abstract class AbstractPersistentTask extends AbstractEntity implements IPersistentTask {
	private static final long serialVersionUID = 8243849896059027893L;

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
