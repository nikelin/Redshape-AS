package com.redshape.applications.bootstrap;

import com.redshape.utils.Commons;
import com.redshape.utils.IPackagesLoader;
import com.redshape.utils.config.IConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    private boolean initialized;
    
    @Autowired( required = true )
    private IConfig config;
    
    @Autowired( required = true )
    private IPackagesLoader packagesLoader;

    public Bootstrap() {
        this( new ArrayList<IBootstrapAction>() );
    }

    public Bootstrap( List<IBootstrapAction> actions ) {
        this.actions = actions;

		this.addAction( new LoggingStarter() );
    }

    public IConfig getConfig() {
    	return this.config;
    }
    
    public void setConfig( IConfig config ) {
    	this.config = config;
    }
    
    protected IPackagesLoader getPackagesLoader() {
    	return this.packagesLoader;
    }
    
    public void setPackagesLoader( IPackagesLoader loader ) {
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
        if ( this.initialized ) {
            return;
        } else {
            this.initialized = true;
        }

        List<IBootstrapAction> actions = this.getActions();
        if ( actions.isEmpty() ) {
            log.info("Nothing to process...");
            return;
        }

        for ( IBootstrapAction action : actions ) {
            if ( !action.hasMarker( ActionMarker.PROCESSED ) ) {
                log.info("Processing action: " + Commons.select( action.getId(), action.getClass().getCanonicalName() ) );
				try {
                	this.runAction( action );
				} catch ( Throwable e ) {
					if ( action.getMarkers().contains(ActionMarker.CRITICAL) ) {
						throw new BootstrapException("Action bootstrap failed", e );
					}

					log.error( String.format(
						"Failed to process action: %s (%s)",
						action.getClass().getCanonicalName(),
						Commons.select( action.getId(), "<unknown>" ) ) );
				}
            }
        }

        log.info("Processed " + actions.size() + " actions.");
        log.info("Bootstrap completed!");
    }

    private void runAction( IBootstrapAction action ) throws BootstrapException {
        try {
            if ( action.hasMarkers( ActionMarker.PROCESSED, ActionMarker.ERROR )
		            || this.isDisabled( action.getId() ) ) {
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
                if ( dependency == null || dependency.hasMarker( ActionMarker.ERROR ) ) {
                    throw new BootstrapException("cannot resolve dependencies ( " + id + " for " + action.getId() + " ) " )  ;
                }

                if ( !dependency.hasMarker( ActionMarker.PROCESSED ) ) {
                    this.runAction( dependency );
                }
            }
        } catch ( BootstrapException e ) {
            action.addMarker( ActionMarker.ERROR );

            if ( action.hasMarker( ActionMarker.CRITICAL ) ) {
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
        action.addMarker( ActionMarker.PROCESSED );
    }

    private boolean isDependenciesResolved( IBootstrapAction action ) {
        for ( Object id : action.getDependencies() ) {
            if ( !this.getAction( id ).hasMarker( ActionMarker.PROCESSED ) ) {
                return false;
            }
        }

        return true;
    }


}
