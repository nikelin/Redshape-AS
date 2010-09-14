package com.redshape.server;

import com.redshape.config.ConfigException;
import com.redshape.exceptions.ExceptionWithCode;
import com.redshape.io.protocols.core.IProtocol;
import com.redshape.persistence.entities.requesters.IRequester;
import com.redshape.server.policy.IPolicy;
import com.redshape.server.policy.PolicyType;
import com.redshape.utils.Registry;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 7, 2010
 * Time: 12:59:31 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractServer implements IServer {
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

    private Map<String, Object> properties = new HashMap<String, Object>();

    private Map<PolicyType, Collection<IPolicy> > policies = new HashMap<PolicyType, Collection<IPolicy>>();

    /**
    * Состояние запущенности сервера { @see com.redshape.server.ApplicationServer State}
    */
    private ServerState state = ServerState.DOWN;

    public AbstractServer( String host, Integer port, Boolean isSSLEnabled ) {
        this.host = host;
        this.port = port;
        this.sslEnabled = isSSLEnabled;
    }

    @Override
    public void setHost( String host ) {
        this.host = host;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public void setPort( Integer port ) {
        this.port = port;
    }

    @Override
    public Integer getPort() {
        return this.port;
    }

    @Override
    public void enableSSL( boolean bool ) throws ServerException {
        this.sslEnabled = bool;
    }

    @Override
    public boolean isSSLEnabled() {
        return this.sslEnabled;
    }

    protected void changeState( ServerState state ) {
        this.state = state;
    }

    @Override
    public boolean isRunning() {
        return this.state.equals( ServerState.RUNNING );
    }

    public boolean isConnectionExpired( IRequester user ) throws ConfigException {
        return new Date().getTime() - user.getLastAccessTime() >= Integer.valueOf( Registry.getConfig().get("sharedSettings").get("sessions").get("lifetime").value() );
    }

    @Override
    public void setProperty( String name, Object value ) {
        this.properties.put( name, value );
    }

    public Object getProperty( String name ) {
        return this.properties.get(name);
    }

    @Override
    public boolean isInitialized() {
        return this.initialized == true;
    }

    public void markInitialized( boolean state ) {
        this.initialized = state;
    }

    @Override
    public void addPolicy( Class<? extends IProtocol> protocolContext, PolicyType type, IPolicy policy ) {
        if ( this.policies.get(type) != null ) {
            this.policies.get(type).add(policy);
            return;
        }

        this.policies.put( type, Arrays.asList( new IPolicy[] { policy } ) );
    }

    public Collection<IPolicy> getPolicies( Class<? extends IProtocol> protocolContext, PolicyType type ) {
        return this.policies.get(type);
    }

    @Override
    public boolean checkPolicy( Class<? extends IProtocol> protocolContext, PolicyType type  ) throws ExceptionWithCode {
        return this.checkPolicy( protocolContext, type, null);
    }

    @Override
    public boolean checkPolicy( Class<? extends IProtocol> protocolContext, PolicyType type, Object data ) throws ExceptionWithCode {
        boolean result = true;

        if ( this.getPolicies( protocolContext, type) == null || this.getPolicies(protocolContext, type).isEmpty() ) {
            return true;
        }

        for ( IPolicy policy : this.getPolicies( protocolContext, type) ) {
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
