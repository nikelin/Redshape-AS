package com.redshape.jobs;

import com.redshape.persistence.entities.AbstractDTO;
import com.redshape.utils.Commons;

import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 4/27/12
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractPersistenceJobDTO extends AbstractDTO implements IPersistenceJob {
    private Long id;
    private UUID jobId;
    private JobStatus state;
    private Integer failuresCount;
    private Date created;
    private Date updated;
    private Date processedDate;

    protected AbstractPersistenceJobDTO( Class<? extends IPersistenceJob> clazz ) {
        super(clazz);
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId( Long id ) {
        this.id = id;
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
        return Commons.select(failuresCount, 0);
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
