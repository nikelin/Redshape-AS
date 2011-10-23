package com.redshape.io;


import com.redshape.io.annotations.InteractionService;
import com.redshape.io.annotations.RequiredPort;
import com.redshape.io.interactors.ServiceID;
import com.redshape.io.net.auth.ICredentialsProvider;
import com.redshape.utils.IPackagesLoader;
import com.redshape.utils.InterfacesFilter;
import com.redshape.utils.PackageLoaderException;
import com.redshape.utils.config.IConfig;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Interactors factory which stores interactions services instances and also provides
 * ability to find associated interactor for given network node.
 *
 * @author nikelin.
 * @todo: Refactor!
 */
public final class InteractorsFactory implements IInteractorsFactory {
    private static final Logger log = Logger.getLogger( InteractorsFactory.class );

    private Collection<String> packages;
    private IPackagesLoader packagesLoader;
    private Map<Class<? extends INetworkConnection>, ICredentialsProvider> credentialsProviders = new HashMap();
    private Map<Class<? extends INetworkConnection>, IConfig> interactorsConfiguration = new HashMap();
    private Collection<Class<? extends INetworkConnection>> interactors = new HashSet();
    private Map<INetworkNode, Collection<INetworkConnection>> nodeInteractors = new HashMap();

    public InteractorsFactory() throws InstantiationException {
        this( new HashSet() );
    }

    public InteractorsFactory( Collection<String> packages ) throws InstantiationException {
        this( packages, false);
    }

    public InteractorsFactory( Collection<String> packages, boolean initialize ) 
    	throws InstantiationException {
        this.packages = packages;

        if ( initialize ) {
            this.initialize();
        }
    }

    public void setPackagesLoader( IPackagesLoader loader ) {
        this.packagesLoader = loader;
    }

    public IPackagesLoader getPackagesLoader() {
        return this.packagesLoader;
    }

    public void setCredentialsProviders( 
    		Map<Class<? extends INetworkConnection>,
    		ICredentialsProvider> providers ) {
        this.credentialsProviders = providers;
    }

    public void addCredentialsProvider( 
    		Class<? extends INetworkConnection> interactorClass,
    		ICredentialsProvider provider ) {
        this.credentialsProviders.put(interactorClass, provider);
    }

    protected ICredentialsProvider getCredentialsProvider( 
    		Class<? extends INetworkConnection> interactor ) {
        return this.credentialsProviders.get(interactor);
    }

    public void setInteractorConfiguration( 
    		Class<? extends INetworkConnection> interactorClass,
    		IConfig config ) {
        this.interactorsConfiguration.put( interactorClass, config );
    }

    public void setInteractorsConfiguration( 
    		Map<Class<? extends INetworkConnection>,
    		IConfig> configuration ) {
        this.interactorsConfiguration = configuration;
    }

    public IConfig getInteractorConfiguration( 
    		Class<? extends INetworkConnection> interactorClazz ) {
        return this.interactorsConfiguration.get(interactorClazz);
    }

    public void addInteractor( 
    		Class<? extends INetworkConnection> interactorClazz )
            throws InstantiationException {
        this.interactors.add( interactorClazz );
    }

    public void addInteractor( INetworkNode node, 
    		INetworkConnection interactor ) {
        this.interactors.add( interactor.getClass() );

        if ( this.nodeInteractors.get(node) == null ) {
            this.nodeInteractors.put( node, new HashSet() );
        }

        this.nodeInteractors.get(node).add( interactor );
    }

    public void setInteractors( 
    		Collection<Class<? extends INetworkConnection>> interactors ) {
        this.interactors = interactors;
    }

    public Collection<Class<? extends INetworkConnection>> getInteractors() {
        return this.interactors;
    }

	@Override
    public INetworkConnection findInteractor( ServiceID serviceId, INetworkNode node )
    	throws InstantiationException {
    	for ( Class<? extends INetworkConnection> interactor : this.getInteractors() ) {
    		InteractionService meta = interactor.getAnnotation( InteractionService.class );
    		if ( meta == null ) {
    			continue;
    		}
    		
    		if ( meta.id() != null && meta.id().toLowerCase().equals( serviceId.name().toLowerCase() ) ) {
    			return this.createInteractor(node, interactor );
    		}
    	}
    	
    	return null;
    }

    public Collection<INetworkConnection> findInteractors( INetworkNode node )
    		throws InstantiationException {
        Collection<INetworkConnection> result = new HashSet<INetworkConnection>();

        for ( Class<? extends INetworkConnection> interactorClazz : this.getInteractors() ) {
            boolean osSatisfied = false;
            InteractionService interactorMeta = interactorClazz.getAnnotation( 
            														InteractionService.class );
            for ( PlatformType type : interactorMeta.platforms() ) {
                if ( PlatformType.isInFamily( node.getPlatformType(), type ) ) {
                    osSatisfied = true;
                    break;
                }
            }

            if ( !osSatisfied ) {
                continue;
            }

            int portsSatisfied = 0;
            for ( RequiredPort requiredPort : interactorMeta.ports() ) {
                boolean satisfied = false;
                for ( NetworkNodePort port : node.getPorts() ) {
                    if ( requiredPort.value() != port.getPortId() ) {
                        continue;
                    }

                    if ( Arrays.binarySearch( requiredPort.protocols(), port.getProtocol(), new Comparator<String>() {
                        public int compare( String object, String subject ) {
                            return object.equals( subject ) ? 1 : -1;
                        }
                    }) != -1 ) {
                        continue;
                    }

                    satisfied = true;
                }

                if ( satisfied ) {
                    portsSatisfied++;
                } else {
                    break;
                }
            }

            if ( portsSatisfied != interactorMeta.ports().length ) {
                continue;
            }


            INetworkConnection interactor = this.createInteractor( node, interactorClazz );
            log.info("Interactor for node " + node.getNetworkPoint() + " has been created.");

            this.addInteractor( node, interactor  );
            result.add( interactor );
        }

        return result;
    }

    protected Collection<INetworkConnection> getInteractors( INetworkNode node ) {
        return this.nodeInteractors.get(node);
    }

    protected INetworkConnection getInteractor( INetworkNode node, String serviceId ) {
        for ( INetworkConnection interactor : this.getInteractors(node) ) {
            if ( interactor.getServiceID().equals( serviceId ) ) {
                return interactor;
            }
        }

        return null;
    }

    public Collection<String> getPackages() {
        return this.packages;
    }

    public void addPackage( String path ) {
        this.packages.add(path);
    }

    protected INetworkConnection createInteractor(
    		INetworkNode node, 
    		Class<? extends INetworkConnection> interactorClazz )
    			throws InstantiationException {
        try {
            INetworkConnection interactorInstance = interactorClazz.getConstructor( INetworkNode.class ).newInstance( node );
            interactorInstance.setCredentialsProvider( this.getCredentialsProvider( interactorClazz ) );
            interactorInstance.setConfig( this.getInteractorConfiguration( interactorClazz ) );

            return interactorInstance;
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new InstantiationException( e.getMessage() );
        }
    }

    public void initialize() throws InstantiationException {
        assert( this.getPackagesLoader() != null );

        try {
            for ( String packagePath : this.getPackages() ) {
                Class<? extends INetworkConnection>[] interactorsClasses =
                                                        this.packagesLoader.<INetworkConnection>getClasses(
                                                            packagePath,
                                                            new InterfacesFilter(
                                                                new Class[] { INetworkConnection.class },
                                                                new Class[] {InteractionService.class },
                                                                true
                                                            )
                                                        );
                for ( Class<? extends INetworkConnection> clazz : interactorsClasses ) {
                    this.addInteractor( clazz );
                }
            }
        } catch ( PackageLoaderException e ) {
            log.error( e.getMessage(), e );
            throw new InstantiationException( e.getMessage() );
        }
    }

}
