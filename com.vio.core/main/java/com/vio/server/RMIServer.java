package com.vio.server;

import com.vio.config.readers.ConfigReaderException;
import com.vio.io.protocols.response.Response;
import com.vio.remoting.annotations.RemoteService;
import com.vio.remoting.interfaces.RemoteInterface;
import com.vio.utils.InterfacesFilter;
import com.vio.utils.PackageLoaderException;
import com.vio.utils.Registry;

import org.apache.log4j.Logger;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.*;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.server
 * @date May 4, 2010
 */
public class RMIServer extends AbstractServer<Response> {
    public static final Logger log = Logger.getLogger( RMIServer.class );
    public static final String DEFAULT_INTERFACES_PACKAGE = "com.vio.remoting.interfaces.impl";

    /**
     * Состояние сервера
     *
     * @var State
     */
    private ServerState state;

    /**
     * Индикатор активности SSL-защиты для текущего сервера
     *
     * @var boolean
     */
    private boolean sslEnabled;

    /**
     * Хост данного RMI-сервера
     *
     * @var String
     */
    private String host;

    /**
     * Порт данного RMI-сервера
     *
     * @var Integer
     */
    private Integer port;

    private boolean initialized;

    /**
     * Дополнительные свойства, поддерживаемые сервером 
     *
     * @var Map<String, Object>
     */
    private Map<String, Object> properties = new HashMap<String, Object>();

    private java.rmi.registry.Registry registry;

    public static enum Property {
        INTERFACES_PACKAGE
    }

    public RMIServer() {
        this(null, null );
    }

    public RMIServer( String host, Integer port ) {
        super();

        this.host = host;
        this.port = port;
    }

    public void setHost( String host ) {
        this.host = host;
    }

    public String getHost() {
        return this.host;
    }

    public void setPort( Integer port ) {
        this.port = port;
    }

    public Integer getPort() {
        return this.port;
    }

    public boolean isSSLEnabled() {
        return this.sslEnabled;
    }

    public void enableSSL( boolean bool ) {
        this.sslEnabled = bool;
    }

    public void initialize() throws ServerException {
        try {
            this.registry = LocateRegistry.createRegistry( this.port );
        } catch ( RemoteException e ) {
            log.error( e.getMessage(), e );
            log.error("Cannot bind RMI registry to " + this.port + " port.");
            throw new ServerException();
        }
    }

    public void startListen() throws ServerException {
        if ( !this.isInitialized() ) {
            this.initialize();
        }

        String interfacesPackage = (String) this.getProperty( RMIServer.Property.INTERFACES_PACKAGE.name() );
        if( interfacesPackage == null ) {
            interfacesPackage = DEFAULT_INTERFACES_PACKAGE;
        }

        try {
            for ( Class<? extends Remote> remotingInterface : this.loadInterfaces(interfacesPackage) ) {
                try {
                    Remote service = remotingInterface.newInstance();
                    String serviceName = this.getServiceName(service);
                    if ( serviceName == null ) {
                        throw new Exception("Service (" + service.getClass().getCanonicalName() + ") must have specified name!" );
                    }

                    log.info("Starting service: " + serviceName );
                    this.registry.rebind( serviceName, service);

                    log.info("Current services list: " + Arrays.asList( this.registry.list() ) );
                } catch( Throwable e ) {
                    log.error( e.getMessage(), e );
                    log.error("Cannot startup service: "  + remotingInterface.getCanonicalName() );
                }
            }
        } catch ( Throwable e ) {
            throw new ServerException();
        }

        this.state = ServerState.RUNNING;
    }

    protected String getServiceName( Remote service ) {
        Class<?> parent = service.getClass();
        RemoteService annon;
        do {
            annon = parent.getAnnotation( RemoteService.class );
        } while ( annon == null && null != ( parent = parent.getSuperclass() ) );

        return annon == null ? null : annon.name();
    }

    public void shutdown() {
        this.state = ServerState.DOWN;
    }

    public boolean isRunning() {
        return this.state == ServerState.RUNNING;
    }

    public void setProperty( String name, Object value ) {
        this.properties.put( name, value );
    }

    public Object getProperty( String name ) {
        return this.properties.get(name);
    }

    public Class<? extends Remote>[] loadInterfaces( String pkgName ) throws ConfigReaderException, PackageLoaderException {
        return Registry.getPackagesLoader().<Remote>getClasses( pkgName, new InterfacesFilter( new Class[] { RemoteInterface.class }, new Class[] { RemoteService.class } ) );
    }

    public boolean isPropertySupports( String name ) {
        return Property.valueOf(name) != null;
    }

    public String getUri() {
        StringBuilder builder = new StringBuilder();

        builder.append("//")
               .append( this.getHost() )
               .append( ":" )
               .append( this.getPort() )
               .append( "/" );

        log.info("Will be binded to: " + builder.toString() );

        return builder.toString();
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public Response createResponseObject() {
        return null;
    }
}
