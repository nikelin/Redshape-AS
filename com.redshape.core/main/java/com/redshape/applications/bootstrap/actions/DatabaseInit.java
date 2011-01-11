package com.redshape.applications.bootstrap.actions;

import com.redshape.applications.bootstrap.AbstractBootstrapAction;
import com.redshape.applications.bootstrap.Action;
import com.redshape.applications.bootstrap.BootstrapException;
import com.redshape.commands.CommandsFactory;
import com.redshape.persistence.Provider;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 22, 2010
 * Time: 2:44:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseInit extends AbstractBootstrapAction {
    private static final Logger log = Logger.getLogger( DatabaseInit.class );

    public DatabaseInit() {
        this.setId( Action.DATABASE_ID);
        this.markCritical();
    }

    public void process() throws BootstrapException {
        try {
            Provider.getEJBFactory();
            
            CommandsFactory.getDefault().addPackage("com.redshape.commands.migrate");
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new BootstrapException();
        }
    }

}
