package com.redshape.commands.migrate;

import com.redshape.commands.AbstractCommand;
import com.redshape.migration.MigrationManager;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 1, 2010
 * Time: 12:41:59 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractMigrateCommand extends AbstractCommand {
    private MigrationManager manager;

    public AbstractMigrateCommand( MigrationManager manager ) {
        this.setManager(manager);
    }

    public void setManager( MigrationManager manager ) {
        this.manager = manager;
    }

    public MigrationManager getManager() {
        return this.manager;
    }

}
