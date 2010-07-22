package com.vio.commands.migrate;

import com.vio.applications.bootstrap.IBootstrap;
import com.vio.applications.bootstrap.actions.DatabaseInit;
import com.vio.commands.AbstractCommand;
import com.vio.migration.MigrationManager;
import com.vio.utils.Registry;

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
        this.correctBootstrap();
    }

    public void setManager( MigrationManager manager ) {
        this.manager = manager;
    }

    public MigrationManager getManager() {
        return this.manager;
    }

    protected void correctBootstrap() {
        IBootstrap boot = Registry.getApplication().getBootstrap();
        boot.clearActionPackages();

        boot.addAction( new DatabaseInit() );
    }

}
