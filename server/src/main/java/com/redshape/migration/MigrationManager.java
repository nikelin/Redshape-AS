package com.redshape.migration;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 26, 2010
 * Time: 2:26:29 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MigrationManager {

    public void migrate( int from, int to ) throws MigrationException;

    public Action getAction( int from, int to );

}
