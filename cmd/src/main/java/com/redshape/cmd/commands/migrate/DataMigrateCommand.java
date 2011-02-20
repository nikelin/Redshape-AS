package com.redshape.cmd.commands.migrate;

import com.redshape.applications.bootstrap.IBootstrapAction;
import com.redshape.commands.ExecutionException;
import com.redshape.commands.migrate.AbstractMigrateCommand;
import com.redshape.migration.DataMigrationManager;
import com.redshape.migration.MigrationException;
import com.redshape.commands.annotations.Command;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 11:07:09 AM
 * To change this template use File | Settings | File Templates.
 */
@Command( module = "migrate", name = "data", helpMessage = "databases data migration from X to Y")
public class DataMigrateCommand extends AbstractMigrateCommand {
    private static final Logger log = Logger.getLogger( DataMigrateCommand.class );

    private static final String to = "to";
    private static final String from = "from";

    private static final String[] PROPERTIES = { to, from };

    public DataMigrateCommand() {
        super( DataMigrationManager.getDefault() );
    }

    @Override
    public void process() throws ExecutionException {
         try {
            this.getManager().migrate( this.getIntegerProperty( from ), this.getIntegerProperty( to ) );
        } catch ( MigrationException e ) {
            log.error( e.getMessage(), e );
            throw new ExecutionException();
        }
    }

    @Override
    public String[] getImportant() {
        return new String[] { to, from };
    }

    @Override
    public String[] getSupported() {
        return PROPERTIES;
    }

    @Override
    public boolean isSupports( String name ) {
        return Arrays.binarySearch( PROPERTIES, name ) != -1;
    }
}
