package com.redshape.io;


import org.apache.log4j.Logger;

import com.redshape.utils.config.IConfig;
import com.redshape.io.annotations.InteractionService;
import com.redshape.io.annotations.RequiredPort;
import com.redshape.io.net.auth.ICredentialsProvider;
import com.redshape.utils.IPackagesLoader;
import com.redshape.utils.InterfacesFilter;
import com.redshape.utils.PackageLoaderException;
import com.redshape.utils.PackagesLoader;

import java.util.*;

/**
 * Interactors factory which stores interactions services instances and also provides
 * ability to find associated interactor for given network node.
 *
 * @author nikelin.
 */
public final class InteractorsFactory implements IInteractorsFactory, IPackagesLoader {
    private static final Logger log = Logger.getLogger( InteractorsFactory.class );

    private Collection<String> packages;
    private PackagesLoader packagesLoader;
    private Map<Class<? extends INetworkInteractor>, ICredentialsProvider> credentialsProviders = new HashMap();
    private Map<Class<? extends INetworkInteractor>, IConfig> interactorsConfiguration = new HashMap();
    private Collection<Class<? extends INetworkInteractor>> interactors = new HashSet();
    private Map<INetworkNode, Collection<INetworkInteractor>> nodeInteractors = new HashMap();

    public InteractorsFactory() throws InstantiationException {
        this( new HashSet() );
    }

    public InteractorsFactory( Collection<String> packages ) throws InstantiationException {
        this( packages, false);
    }

    public InteractorsFactory( Collection<String> packages, boolean initialize ) throws InstantiationException {
        this.packages = packages;

        if ( initialize ) {
            this.initialize();
        }
    }

    public void setPackagesLoader( PackagesLoader loader ) {
        this.packagesLoader = loader;
    }

    public PackagesLoader getPackagesLoader() {
        return this.packagesLoader;
    }

    public void setCredentialsProviders( Map<Class<? extends INetworkInteractor>, ICredentialsProvider> providers ) {
        this.credentialsProviders = providers;
    }

    public void addCredentialsProvider( Class<? extends INetworkInteractor> interactorClass, ICredentialsProvider provider ) {
        this.credentialsProviders.put(interactorClass, provider);
    }

    protected ICredentialsProvider getCredentialsProvider( Class<? extends INetworkInteractor> interactor ) {
        return this.credentialsProviders.get(interactor);
    }

    public void setInteractorConfiguration( Class<? extends INetworkInteractor> interactorClass, IConfig config ) {
        this.interactorsConfiguration.put( interactorClass, config );
    }

    public void setInteractorsConfiguration( Map<Class<? extends INetworkInteractor>, IConfig> configuration ) {
        this.interactorsConfiguration = configuration;
    }

    public IConfig getInteractorConfiguration( Class<? extends INetworkInteractor> interactorClazz ) {
        return this.interactorsConfiguration.get(interactorClazz);
    }

    public void addInteractor( Class<? extends INetworkInteractor> interactorClazz )
                                                        throws InstantiationException {
        this.interactors.add( interactorClazz );
    }

    public void addInteractor( INetworkNode node, INetworkInteractor interactor ) {
        this.interactors.add( interactor.getClass() );

        if ( this.nodeInteractors.get(node) == null ) {
            this.nodeInteractors.put( node, new HashSet() );
        }

        this.nodeInteractors.get(node).add( interactor );
    }

    public void setInteractors( Collection<Class<? extends INetworkInteractor>> interactors ) {
        this.interactors = interactors;
    }

    public Collection<Class<? extends INetworkInteractor>> getInteractors() {
        return this.interactors;
    }

    public INetworkInteractor findInteractor( INetworkNode node, String serviceId ) throws InstantiationException {
        Collection<INetworkInteractor> interactors = this.findInteractors( node );
        for ( INetworkInteractor interactor : interactors ) {
            if ( interactor.getServiceID().equals( serviceId ) ) {
                return interactor;
            }
        }

        return null;
    }

    public Collection<INetworkInteractor> findInteractors( INetworkNode node ) throws InstantiationException {
        Collection<INetworkInteractor> result = new HashSet<INetworkInteractor>();

        for ( Class<? extends INetworkInteractor> interactorClazz : this.getInteractors() ) {
            boolean osSatisfied = false;
            InteractionService interactorMeta = interactorClazz.getAnnotation( InteractionService.class );
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


            INetworkInteractor interactor = this.createInteractor( node, interactorClazz );
            log.info("Interactor for node " + node.getNetworkPoint() + " has been created.");

            this.addInteractor( node, interactor  );
            result.add( interactor );
        }

        return result;
    }

    protected Collection<INetworkInteractor> getInteractors( INetworkNode node ) {
        return this.nodeInteractors.get(node);
    }

    protected INetworkInteractor getInteractor( INetworkNode node, String serviceId ) {
        for ( INetworkInteractor interactor : this.getInteractors(node) ) {
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

    protected INetworkInteractor createInteractor( INetworkNode node, Class<? extends INetworkInteractor> interactorClazz ) throws InstantiationException {
        try {
            INetworkInteractor interactorInstance = interactorClazz.getConstructor( INetworkNode.class ).newInstance( node );
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
                Class<? extends INetworkInteractor>[] interactorsClasses =
                                                        this.packagesLoader.<INetworkInteractor>getClasses(
                                                            packagePath,
                                                            new InterfacesFilter(
                                                                new Class[] { INetworkInteractor.class },
                                                                new Class[] {InteractionService.class },
                                                                true
                                                            )
                                                        );
                for ( Class<? extends INetworkInteractor> clazz : interactorsClasses ) {
                    this.addInteractor( clazz );
                }
            }
        } catch ( PackageLoaderException e ) {
            log.error( e.getMessage(), e );
            throw new InstantiationException( e.getMessage() );
        }
    }

}
