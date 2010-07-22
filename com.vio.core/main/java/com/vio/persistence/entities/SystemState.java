package com.vio.persistence.entities;

import com.vio.persistence.managers.Manager;
import com.vio.persistence.managers.ManagerException;
import com.vio.persistence.managers.ManagersFactory;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 17, 2010
 * Time: 1:19:08 AM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Entity( name = "system_state" )
public class SystemState extends AbstractEntity<SystemState> {
    @Transient
    private static SystemState instance = null;

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

    private SystemState() {}

    public static SystemState getInstance() throws ManagerException {
        if ( instance == null ) {
            instance = createSingle();
        }

        return instance;
    }

    protected static SystemState createSingle() throws ManagerException {
        List<SystemState> results = (List<SystemState>) ManagersFactory.getDefault().getForEntity( SystemState.class ).findAll();
        if ( results.size() > 0 ) {
            return results.get(0);
        }

        SystemState object = new SystemState();

        return object;
    }

    public void updateVersion( Integer version ) throws ManagerException {
        this.version = version;
        this.getDAO().save( this );
    }

    public Integer getVersion() {
        return this.version;
    }

    public void updateLastStartupTime() throws ManagerException {
        this.lastStartupTime = new Date().getTime();
        this.getDAO().save(this);
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

    public void updateLastShutdownTime() throws ManagerException {
        this.lastShutdownTime = new Date().getTime();
        this.getDAO().save(this);
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
