package com.redshape.cmd.commands.migrate;

import com.redshape.commands.AbstractCommand;
import com.redshape.commands.ExecutionException;
import com.redshape.commands.annotations.Command;
import com.redshape.persistence.Provider;
import com.redshape.persistence.managers.ManagerException;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Sep 27, 2010
 * Time: 3:38:08 PM
 * To change this template use File | Settings | File Templates.
 */
@Command( module = "migrate", name = "rebuild-schema", helpMessage = "rebuild database schema")
public class DatabaseSetupCommand extends AbstractCommand {
    private static final Logger log = Logger.getLogger( DatabaseSetupCommand.class );

    @Override
    public void process() throws ExecutionException {
        try {
            Provider.getEJBFactory(true);
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ExecutionException();
        }
    }

    @Override
    public boolean isSupports( String action ) {
        return false;
    }

    @Override
    public String[] getSupported() {
        return new String[] {};
    }

}
