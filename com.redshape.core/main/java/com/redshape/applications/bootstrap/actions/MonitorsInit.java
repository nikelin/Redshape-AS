package com.redshape.applications.bootstrap.actions;

import com.redshape.applications.bootstrap.AbstractBootstrapAction;
import com.redshape.applications.bootstrap.Action;
import com.redshape.applications.bootstrap.BootstrapException;
import com.redshape.config.ConfigException;
import com.redshape.config.IConfig;
import com.redshape.daemons.HeapMonitorTask;
import com.redshape.daemons.PluginsLoaderTask;
import com.redshape.utils.Constants;
import com.redshape.utils.Registry;
import org.apache.log4j.Logger;

import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 22, 2010
 * Time: 2:51:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class MonitorsInit extends AbstractBootstrapAction {
    private static final Logger log = Logger.getLogger( MonitorsInit.class );

    public MonitorsInit() {
        this.setId( Action.MONITORS_ID);
        this.addDependency( Action.API_ID );
        this.markCritical();
    }

    public void process() throws BootstrapException {
        try {
            for ( IConfig node : Registry.getConfig().get("services").childs() ) {
                try {
                    Registry.getTicker().scheduleAtFixedRate(
                            (TimerTask) Class.forName( node.get("class").value() ).newInstance(),
                            node.get("waiting").isNull() ? 0 : Integer.valueOf( node.get("waiting").value() ),
                            node.get("period").isNull() ? 1000 : Integer.valueOf( node.get("period").value() )
                    );
                } catch ( Throwable e ) {
                    log.error("Cannot start " + ( node.get("name").isNull() ? "<null>" : node.get("name").value() ) + " service...", e);
                }
            }
        } catch ( ConfigException e ) {
            log.error( e.getMessage(), e );
            throw new BootstrapException();
        }
    }

}
