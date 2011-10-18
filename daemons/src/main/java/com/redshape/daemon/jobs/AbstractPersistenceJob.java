package com.redshape.daemon.jobs;

import com.redshape.persistence.entities.IEntity;
import com.redshape.utils.Commons;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 15.06.11
 * Time: 12:36
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public abstract class AbstractPersistenceJob implements IPersistenceJob, IEntity {
    @Basic
    private UUID jobId;

    @Enumerated( EnumType.STRING )
    private JobStatus state;

    @Basic
    private Integer failuresCount;

    @Temporal( TemporalType.DATE )
    private Date created;

	@Temporal( TemporalType.DATE )
	private Date updated;

    @Temporal( TemporalType.DATE )
    private Date processedDate;

    protected AbstractPersistenceJob() {
        this.jobId = UUID.randomUUID();
        this.failuresCount = 0;
        this.state = JobStatus.WAITING;
    }

    @Override
    public JobStatus getState() {
        return state;
    }

    @Override
    public void setState(JobStatus state) {
        this.state = state;
    }

	@Override
	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@Override
    public void increaseFailuresCount() {
        this.failuresCount = this.getFailuresCount() + 1;
    }

    @Override
    public Integer getFailuresCount() {
        return Commons.select( failuresCount, 0 );
    }

    public void setFailuresCount(Integer failuresCount) {
        this.failuresCount = failuresCount;
    }

    @Override
    public UUID getJobId() {
        return this.jobId;
    }

    @Override
    public void setJobId(UUID id) {
        this.jobId = id;
    }

    @Override
    public Date getCreated() {
        return this.created;
    }

    public Date getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }
}
