package com.vio.applications.bootstrap;

import com.vio.applications.Application;
import com.vio.utils.InterfacesFilter;
import com.vio.utils.PackageLoaderException;
import com.vio.utils.Registry;
import org.apache.log4j.Logger;

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

    public static String defaultActionsPackage = "com.vio.applications.bootstrap.actions";

    private List<IBootstrapAction> actions = new ArrayList<IBootstrapAction>();
    private Map<Object, Boolean> actionsStatus = new HashMap<Object, Boolean>();
    private Set<String> packages = new HashSet<String>();

    public Bootstrap() {
        this.addActionsPackage( defaultActionsPackage );   
    }

    public List<IBootstrapAction> getActions() {
        return this.actions;
    }

    public void removeAction( Object id ) {
        this.actions.remove(id);
    }

    public void addAction( IBootstrapAction action ) {
        action.setBootstrap( this );
        this.actions.add(action);
    }

    public void removeActionsPackage( String packagePath ) {
        this.packages.remove(packagePath);
    }

    public void clearActionPackages() {
        this.packages.clear();
    }

    public void clearActions() {
        this.actions.clear();;
    }

    public void addActionsPackage( String packagePath ) {
        this.packages.add(packagePath);
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
    public void init() throws BootstrapException {
        try {
            this.loadPackages();
        } catch ( PackageLoaderException e ) {
            throw new BootstrapException();
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

    public void _runAction( IBootstrapAction action ) throws BootstrapException {
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

    protected void loadPackages() throws PackageLoaderException {
        for ( String path : this.packages ) {
            Class<? extends IBootstrapAction>[] actionClasses = Registry.getPackagesLoader()
                                                                      .<IBootstrapAction>getClasses(
                                                                            path,
                                                                            new InterfacesFilter(
                                                                                new Class[] { IBootstrapAction.class }
                                                                            )
                                                                      );
            for ( Class<? extends IBootstrapAction> actionClass : actionClasses ) {
                try {
                    this.addAction( actionClass.newInstance() );
                } catch ( Throwable e ) {
                    log.error( "Cannot load action " + actionClass.getCanonicalName() + " from package", e);
                    continue;
                }
            }
        }
    }


}
