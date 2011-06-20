package com.redshape.daemon.jobs;

import com.redshape.persistence.entities.AbstractEntity;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
public abstract class AbstractPersistenceJob extends AbstractEntity implements IJob {
    @Basic
    private UUID jobId;

    @Temporal( TemporalType.DATE )
    private Date created;

    @Temporal( TemporalType.DATE )
    private Date processedDate;

    protected AbstractPersistenceJob() {
        this.jobId = UUID.randomUUID();
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
