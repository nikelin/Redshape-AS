package com.vio.server;

import com.vio.config.readers.ConfigReaderException;
import com.vio.exceptions.ExceptionWithCode;
import com.vio.io.protocols.readers.IRequestReader;
import com.vio.io.protocols.response.IResponse;
import com.vio.io.protocols.writers.IResponseWriter;
import com.vio.persistence.entities.requesters.IRequester;
import com.vio.server.policy.IPolicy;
import com.vio.server.policy.PolicyType;
import com.vio.utils.Registry;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 7, 2010
 * Time: 12:59:31 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractServer<R extends IResponse> implements IServer<R> {
    private final static Logger log = Logger.getLogger( AbstractServer.class );

    /**
     * Хост, к которому привязан данный объект сервера
     */
    private String host;

    private boolean initialized;

    /**
     * Порт, к которому привязан данный объект сервера
     */
    private Integer port;

    private Boolean sslEnabled;

    private IRequestReader reader;

    private IResponseWriter writer;

    private Map<String, Object> properties = new HashMap<String, Object>();

    private Map<PolicyType, Collection<IPolicy> > policies = new HashMap<PolicyType, Collection<IPolicy>>();

    /**
    * Состояние запущенности сервера { @see com.vio.server.ApiServer State}
    */
    private ServerState state = ServerState.DOWN;

    public AbstractServer() {}

    public AbstractServer( String host, Integer port, Boolean isSSLEnabled,
                                    IResponseWriter writer, IRequestReader reader ) {
        this.host = host;
        this.port = port;
        this.writer = writer;
        this.reader = reader;
        this.sslEnabled = isSSLEnabled;
    }

    public void initialize() throws ServerException {
        return;
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

    public void enableSSL( boolean bool ) throws ServerException {
        this.sslEnabled = bool;
    }

    public boolean isSSLEnabled() {
        return this.sslEnabled;
    }

    public IResponseWriter getWriter() {
        return this.writer;
    }

    public void setWriter( IResponseWriter writer ) {
        this.writer = writer;
    }

    public void setReader( IRequestReader reader ) {
        this.reader = reader;
    }

    public IRequestReader getReader() {
        return this.reader;
    }

    protected void changeState( ServerState state ) {
        this.state = state;
    }

    public boolean isRunning() {
        return this.state.equals( ServerState.RUNNING );
    }

    public boolean isConnectionExpired( IRequester user ) throws ConfigReaderException {
        return new Date().getTime() - user.getLastAccessTime() >= ( Registry.getServerConfig() ).getSessionLifeTime();
    }

    public void setProperty( String name, Object value ) {
        this.properties.put( name, value );
    }

    public Object getProperty( String name ) {
        return this.properties.get(name);
    }

    public boolean isInitialized() {
        return this.initialized == true;
    }

    public void markInitialized( boolean state ) {
        this.initialized = state;
    }

    public void addPolicy( PolicyType type, IPolicy policy ) {
        if ( this.policies.get(type) != null ) {
            this.policies.get(type).add(policy);
            return;
        }

        this.policies.put( type, Arrays.asList( new IPolicy[] { policy } ) );
    }

    public Collection<IPolicy> getPolicies( PolicyType type ) {
        return this.policies.get(type);
    }

    public boolean checkPolicy( PolicyType type  ) throws ExceptionWithCode {
        return this.checkPolicy( type, null);
    }

    public boolean checkPolicy( PolicyType type, Object data ) throws ExceptionWithCode {
        boolean result = true;

        if ( this.getPolicies(type) == null || this.getPolicies(type).isEmpty() ) {
            return true;
        }

        for ( IPolicy policy : this.getPolicies(type) ) {
            policy.resetLastException();
            
            result = policy.applicate(data);

            ExceptionWithCode exception = policy.getLastException();
            if ( exception != null ) {
                throw exception;
            }

            if ( !result ) {
                break;
            }
        }

        return result;
    }

}
