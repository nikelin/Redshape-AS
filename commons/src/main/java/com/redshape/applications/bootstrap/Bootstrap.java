package com.redshape.applications.bootstrap;

import com.redshape.config.IConfig;
import com.redshape.utils.InterfacesFilter;
import com.redshape.utils.PackageLoaderException;
import com.redshape.utils.PackagesLoader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 22, 2010
 * Time: 2:18:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class Bootstrap implements IBootstrap {
    private static final Logger log = Logger.getLogger( Bootstrap.class );

    private List<IBootstrapAction> actions = new ArrayList<IBootstrapAction>();
    
    private Map<Object, Boolean> actionsStatus = new HashMap<Object, Boolean>();
    
    private boolean inited;
    
    @Autowired( required = true )
    private IConfig config;
    
    @Autowired( required = true )
    private PackagesLoader packagesLoader;
    
    public IConfig getConfig() {
    	return this.config;
    }
    
    public void setConfig( IConfig config ) {
    	this.config = config;
    }
    
    protected PackagesLoader getPackagesLoader() {
    	return this.packagesLoader;
    }
    
    public void setPackagesLoader( PackagesLoader loader ) {
    	this.packagesLoader = loader;
    }
    
    public List<IBootstrapAction> getActions() {
        return this.actions;
    }

    public void setActions( List<IBootstrapAction> actions ) {
    	this.actions = actions;
    }
    
    synchronized public void removeAction( Object id ) {
        this.actions.remove(id);
    }

    synchronized public void addAction( IBootstrapAction action ) {
        action.setBootstrap( this );
        this.actions.add(action);
    }

    public void clearActions() {
        this.actions.clear();
    }

    public void clear() {
        this.clearActions();
    }

    public void enableAction( Object id ) {
        this.actionsStatus.put(id, true);
    }

    public void disableAction( Object id ) {
        this.actionsStatus.put(id, false);
    }

    public IBootstrapAction getAction( Object id ) {
        for ( IBootstrapAction action : this.actions ) {
            if ( action.getId().equals(id) ) {
                return action;
            }
        }

        return null;
    }

    @Override
    synchronized public void init() throws BootstrapException {
        if ( this.inited ) {
            return;
        } else {
            this.inited = true;
        }

        List<IBootstrapAction> actions = this.getActions();
        if ( actions.isEmpty() ) {
            log.info("Nothing to process...");
            return;
        }

        for ( IBootstrapAction action : actions ) {
            if ( !action.isProcessed() ) {
                log.info("Processing action: " + action.getId() );
                this.runAction( action );
            }
        }

        log.info("Processed " + actions.size() + " actions.");
        log.info("Bootstrap completed!");
    }

    private void runAction( IBootstrapAction action ) throws BootstrapException {
        try {
            if ( action.isProcessed() || action.isError() || this.isDisabled( action.getId() ) ) {
                return;
            }

            if (  !action.hasDependencies() || this.isDependenciesResolved(action) ) {
                this._runAction(action);
                return;
            }

            log.info("Processing action dependencies ( " + action.getDependencies().size() + " )");
            for( Object id : action.getDependencies() ) {
                log.info("Processing dependency: " + id );
                IBootstrapAction dependency = this.getAction(id);
                if ( dependency == null || dependency.isError() ) {
                    throw new BootstrapException("cannot resolve dependencies ( " + id + " for " + action.getId() + " ) " )  ;
                }

                if ( !dependency.isProcessed() ) {
                    this.runAction( dependency );
                }
            }
        } catch ( BootstrapException e ) {
            action.markError();

            if ( action.isCritical() ) {
                log.info("Critical bootstrapping exception", e );
                throw e;
            } else {
                log.info("Non-critical bootstrapping exception", e );
            }
        }
    }

    private boolean isDisabled( Object id ) {
        return this.actionsStatus.containsKey(id);
    }

    protected void _runAction( IBootstrapAction action ) throws BootstrapException {
        action.process();
        action.markProcessed();
    }

    private boolean isAllProcessed() {
        for ( IBootstrapAction action : this.getActions() ) {
            if ( !action.isError() && !action.isProcessed() ) {
                return false;
            }
        }

        return true;
    }

    private boolean hasDependers( Object id ) {
        for ( IBootstrapAction actionObject : this.getActions() ) {
            if ( actionObject.getDependencies().contains( id ) ) {
                return true;
            }
        }

        return false;
    }

    private boolean isDependenciesResolved( IBootstrapAction action ) {
        for ( Object id : action.getDependencies() ) {
            if ( !this.getAction( id ).isProcessed() ) {
                return false;
            }
        }

        return true;
    }


}
