package com.vio.server;

import com.vio.io.protocols.core.IProtocol;
import com.vio.server.policy.IPolicy;
import com.vio.server.policy.PolicyType;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections.map.MultiKeyMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 2:39:58 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractServerFactory implements IServerFactory {
    private MultiKeyMap policies = new MultiKeyMap();    
    private Map<String, Object> properties = new HashMap<String, Object>();

    public AbstractServerFactory() {
        this( new HashMap() );
    }

    public AbstractServerFactory( Map<String, Object> properties ) {
        this.properties = properties;
    }

    protected void bindProperties( IServer server, Map<String, Object> properties ) {
        for ( String name : properties.keySet() ) {
            if ( server.isPropertySupports(name) ) {
                server.setProperty( name, properties.get(name) );
            }
        }
    }

    protected void bindPolicies( IServer server, MultiKeyMap policies ) {
        for ( final Object itemKey : policies.keySet() ) {
            final IPolicy policy = (IPolicy) policies.get(itemKey);
            final Object[] keys = ( (MultiKey) itemKey ).getKeys();

            server.addPolicy( (Class<? extends IProtocol>) keys[0], (PolicyType) keys[1], policy );
        }
    }

    public void addPolicy( Class<? extends IProtocol> protocolContext, PolicyType type, IPolicy policy ) {
        this.policies.put( protocolContext, type, policy );
    }

    public MultiKeyMap getPolicies() {
        return this.policies;
    }

    public void setProperties( Map<String, Object> properties ) {
        this.properties = properties;
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }

    public Object getProperty( String name ) {
        return this.properties.get(name);
    }

    public void setProperty( String name, Object value ) {
        this.properties.put( name, value );
    }
    
}
