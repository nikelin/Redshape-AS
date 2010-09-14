package com.redshape.migration.strategy;

import com.redshape.migration.MigrationException;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 26, 2010
 * Time: 1:01:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MigrationStrategy<T> {

    public void execute( T objects, int from, int to ) throws MigrationException;

    public boolean isAffectable( int version, int from, int to ) throws MigrationException;
    
}
