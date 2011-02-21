package com.redshape.server;

import com.redshape.api.requesters.IRequester;
import com.redshape.io.server.IServer;
import com.redshape.io.server.ServerException;
import com.redshape.io.server.ServerState;
import com.redshape.io.server.policy.ApplicationResult;
import com.redshape.io.server.policy.IPolicy;
import com.redshape.io.server.policy.PolicyType;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 7, 2010
 * Time: 12:59:31 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractServer<T, V> implements IServer<T, V> {
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

    @Autowired( required = true )
    private IConfig config;
    
    private Boolean sslEnabled;

    private Map<String, Object> properties = new HashMap<String, Object>();

    private Map<PolicyType, List<IPolicy<V>>> policies = new HashMap<PolicyType, List<IPolicy<V>>>();

    /**
    * Состояние запущенности сервера { @see com.redshape.server.ApplicationServer State}
    */
    private ServerState state = ServerState.DOWN;

    public AbstractServer( String host, Integer port, Boolean isSSLEnabled ) {
        this.host = host;
        this.port = port;
        this.sslEnabled = isSSLEnabled;
    }

    protected IConfig getConfig() {
    	return this.config;
    }
    
    public void setConfig( IConfig config ) {
    	this.config = config;
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
        return new Date().getTime() - user.getLastAccessTime() >= Integer.valueOf( this.getConfig().get("sharedSettings").get("sessions").get("lifetime").value() );
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

    @SuppressWarnings("unchecked")
	@Override
    public void addPolicy( Class<T> protocolContext, PolicyType type, IPolicy<V> policy ) {
        if ( this.policies.get(type) != null ) {
            this.policies.get(type).add(policy);
            return;
        }

        this.policies.put( type, Arrays.asList( (IPolicy<V>[]) new IPolicy[] { policy } ) );
    }

    @Override
    public List<IPolicy<V>> getPolicies( Class<T> protocolContext, PolicyType type ) {
        return this.policies.get(type);
    }

    @Override
    public ApplicationResult checkPolicy( Class<T> protocolContext, PolicyType type  ) {
        return this.checkPolicy( protocolContext, type, null);
    }

    @Override
    public ApplicationResult checkPolicy( Class<T> protocolContext, PolicyType type, V data ) {
        ApplicationResult result = new ApplicationResult();

        log.info("Initiation validation procedure for object " + (data != null ? data.getClass().getCanonicalName() : "<null>") + " in context of " + type.name() );

        if ( this.getPolicies( protocolContext, type) == null || this.getPolicies(protocolContext, type).isEmpty() ) {
            log.info("There is no policies applied to " + this.getClass().getCanonicalName() + " server.");
            result.markVoid();
            return result;
        }

        for ( IPolicy<V> policy : this.getPolicies( protocolContext, type) ) {
            log.info("Attempting policy " + policy.getClass().getCanonicalName() + " to check request validity.");
            result = policy.applicate( data );

            if ( !result.isSuccessful() ) {
                log.info("Check procedure was finished with failure...");
                break;
            }
        }

        if ( result.isSuccessful() ) {
            log.info("Check has been successful");
        }

        return result;
    }

}