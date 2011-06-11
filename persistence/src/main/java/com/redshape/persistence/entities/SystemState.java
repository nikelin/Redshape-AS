package com.redshape.persistence.entities;

import com.redshape.persistence.dao.ManagerException;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 17, 2010
 * Time: 1:19:08 AM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Entity( name = "system_state" )
public class SystemState extends AbstractEntity {
	private static final long serialVersionUID = -2852778677252545358L;

    @Basic
    @Column( name = "version" )
    private Integer version;

    @Basic
    @Column( name = "last_startup_time" )
    private Long lastStartupTime;

    @Basic
    @Column( name = "first_startup_time" )
    private Long firstStartupTime;

    @Basic
    @Column( name = "last_shutdown_time" )
    private Long lastShutdownTime;

    private SystemState() {
    	super();
    }

    public void updateVersion( Integer version ) throws ManagerException {
        this.version = version;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setLastStartupTime() {
        this.lastStartupTime = new Date().getTime();
    }

    public Long getLastStartupTime() {
        return this.lastStartupTime;
    }

    public boolean isFirstTime() {
        return this.getFirstStartupTime() == null;
    }

    public void updateFirstStartupTime() {
        this.firstStartupTime = new Date().getTime();
    }

    public Long getFirstStartupTime() {
        return this.firstStartupTime;
    }

    public void setLastShutdownTime() {
        this.lastShutdownTime = new Date().getTime();
    }

    public Long getLastShutdownTime() {
        return this.lastShutdownTime;
    }

    public boolean equals( SystemState state ) {
        return true;
    }

    public int hashCode() {
        return this.firstStartupTime.hashCode();
    }

}
