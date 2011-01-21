package com.redshape.server;

import com.redshape.io.protocols.core.IProtocol;
import com.redshape.server.policy.IPolicy;
import com.redshape.server.policy.PolicyType;
import org.apache.commons.collections.map.MultiKeyMap;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 2, 2010
 * Time: 2:24:45 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IServerFactory {

    /**
     * Attemp to create new product
     * @param clazz
     * @param host
     * @param port
     * @param <T>
     * @return
     * @throws InstantiationException
     */
    public <T extends IServer> T newInstance(
            Class<T> clazz, String host, Integer port
    ) throws InstantiationException;

    /**
     * Attemp to create new product
     * @param clazz
     * @param host
     * @param port
     * @param <T>
     * @return
     * @throws InstantiationException
     */    
    public <T extends IServer> T newInstance(
            Class<T> clazz, String host, Integer port, Boolean isSSLEnabled
    ) throws InstantiationException;

    public <T extends IServer> T newInstance(
            Class<T> clazz, String host, Integer port, Boolean isSSLEnabled, Map<String, Object> properties
    ) throws InstantiationException;

    /**
     * Add grobal security policy
     * @param protocolContext
     * @param type
     * @param policy
     */
    public void addPolicy( Class<? extends IProtocol> protocolContext, PolicyType type, IPolicy policy );

    /**
     * Get all global security policies applied to current factory
     * @return
     */
    public MultiKeyMap getPolicies();

    public void setProperties( Map<String, Object> properties );

    public Map<String, Object> getProperties();
}
