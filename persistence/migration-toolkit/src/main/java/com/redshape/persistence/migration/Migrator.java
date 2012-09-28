package com.redshape.persistence.migration;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 26, 2010
 * Time: 12:21:25 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Migrator {
    
    public Integer getVersion();

    public void setUp() throws MigrationException;

    public void rollback() throws MigrationException;

    public boolean isReductant( Action action ) throws MigrationException;

}
