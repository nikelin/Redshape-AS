package com.redshape.server;

import com.redshape.api.InvokableEntitiesRegistry;
import com.redshape.io.protocols.core.response.Response;
import com.redshape.remoting.interfaces.RemoteInterface;
import org.apache.log4j.Logger;
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
public class RMIServer extends AbstractServer {
    public static final Logger log = Logger.getLogger( RMIServer.class );

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
        this(host, port, false);
    }

    public RMIServer( String host, Integer port, Boolean isSSLEnabled ) {
        super( host,port, isSSLEnabled );
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

        Map<String, RemoteInterface> services = InvokableEntitiesRegistry.getRegistered();
        for ( String remoteServiceId : services.keySet() ) {
            try {
                this.registry.bind( remoteServiceId, services.get( remoteServiceId ) );
            } catch ( Throwable e ) {
                log.error( e.getMessage(), e );
                throw new ServerException();
            }
        }

        this.changeState( ServerState.RUNNING );
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
