package com.redshape.cmd.commands.migrate;

import com.redshape.commands.ExecutionException;
import com.redshape.commands.annotations.Command;
import com.redshape.migration.commands.AbstractMigrateCommand;
import com.redshape.migration.EntitiesMigrationManager;
import com.redshape.migration.MigrationException;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 21, 2010
 * Time: 5:32:09 PM
 * To change this template use File | Settings | File Templates.
 */
@Command( module = "migrate", name = "entities", helpMessage = "database schema migration from X to Y version where Y is newest")
public class EntitiesMigrateCommand extends AbstractMigrateCommand {
    private final static Logger log = Logger.getLogger( EntitiesMigrateCommand.class );

    private final static String from = "from";
    private final static String to = "to";

    private final static String[] PROPERTIES = new String[]{ from, to };

    public EntitiesMigrateCommand() {
        super( EntitiesMigrationManager.getDefault() );
    }

    public void process() throws ExecutionException {
        try {
            this.getManager().migrate( this.getIntegerProperty( EntitiesMigrateCommand.from ), this.getIntegerProperty( EntitiesMigrateCommand.to ) );
        } catch ( MigrationException e ) {
            log.error( e.getMessage(), e );
            throw new ExecutionException();
        }
    }

    @Override
    public boolean isSupports( String name ) {
        return Arrays.binarySearch( PROPERTIES, name ) != -1;
    }

    @Override
    public String[] getImportant() {
        return new String[] { to, from };
    }

    @Override
    public String[] getSupported() {
        return PROPERTIES;
    }
}
