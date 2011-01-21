package com.redshape.applications.bootstrap.actions;

import com.redshape.applications.bootstrap.AbstractBootstrapAction;
import com.redshape.applications.bootstrap.Action;
import com.redshape.applications.bootstrap.BootstrapException;
import com.redshape.config.ConfigException;
import com.redshape.config.IConfig;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 22, 2010
 * Time: 2:51:00 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class MonitorsInit extends AbstractBootstrapAction {
    private static final Logger log = Logger.getLogger( MonitorsInit.class );
    private Timer timer;
    
    public MonitorsInit() {
    	this.timer = new Timer();
    	
        this.setId( Action.MONITORS_ID);
        this.addDependency( Action.API_ID );
        this.markCritical();
    }

    protected Timer getTimer() {
    	return this.timer;
    }
    
    public void process() throws BootstrapException {
        try {
            for ( IConfig node : this.getConfig().get("services").childs() ) {
                try {
                    this.getTimer().scheduleAtFixedRate(
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
            throw new BootstrapException( e.getMessage(), e );
        }
    }

}
