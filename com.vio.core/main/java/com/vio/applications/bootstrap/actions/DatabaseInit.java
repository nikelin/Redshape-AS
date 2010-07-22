package com.vio.applications.bootstrap.actions;

import com.vio.applications.bootstrap.AbstractBootstrapAction;
import com.vio.applications.bootstrap.Action;
import com.vio.applications.bootstrap.BootstrapException;
import com.vio.commands.CommandsFactory;
import com.vio.persistence.Provider;
import org.apache.log4j.Logger;
import org.hibernate.cfg.AnnotationConfiguration;

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
            
            CommandsFactory.getDefault().addPackage("com.vio.commands.migrate");
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new BootstrapException();
        }
    }

}
