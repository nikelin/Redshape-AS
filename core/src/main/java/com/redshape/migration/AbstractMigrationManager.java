package com.redshape.migration;

import com.redshape.migration.strategy.MigrationStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 10:57:14 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractMigrationManager implements MigrationManager {
    private Map<Action, MigrationStrategy> strategies = new HashMap<Action, MigrationStrategy>();

    public void setStrategy( Action action, MigrationStrategy strategy ) {
        this.strategies.put( action, strategy );
    }

    public MigrationStrategy getStrategy( Action action ) {
        return this.strategies.get(action);
    }

    public Action getAction( int from, int to ) {
        if ( from > to ) {
            return Action.ROLLBACK;
        } else {
            return Action.UPDATE;
        }
    }
}
