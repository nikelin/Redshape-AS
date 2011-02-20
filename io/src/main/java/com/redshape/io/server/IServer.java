package com.redshape.io.server;

import java.io.IOException;
import java.util.Collection;

import com.redshape.io.server.policy.ApplicationResult;
import com.redshape.io.server.policy.IPolicy;
import com.redshape.io.server.policy.PolicyType;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.server
 * @date May 4, 2010
 */
public interface IServer<T, V> {

    public void setHost( String host );

    public String getHost();

    public void setPort( Integer port );

    public Integer getPort();

    /**
     * Enabled SSL protection for given server
     * @param bool
     * @throws ServerException
     */
    public void enableSSL( boolean bool ) throws ServerException;

    /**
     * Check that SSL protection is enabled
     * @return boolean
     */
    public boolean isSSLEnabled();

    /**
     * Activate connections listening loop
     * @throws ServerException
     * @throws IOException
     */
    public void startListen() throws ServerException, IOException;

    /**
     * Checks that current server do listening at the moment
     * @return
     */
    public boolean isRunning();

    /**
     * Stop current server instance and unbind it from a socket
     */
    public void shutdown();

    public void setProperty( String name, Object value );

    public boolean isPropertySupports( String name );

    /**
     * Does this server
     * @return
     */
    public boolean isInitialized();

    public void initialize() throws ServerException ;
    
    /**
     * Get all policies related given type
     * @param type
     * @return
     */
    public Collection<IPolicy<V>> getPolicies( Class<T> context, PolicyType type );

    /**
     * Add new server policy
     * @param type Category what policy related on
     * @param policy Policy
     */
    public void addPolicy( Class<T> protocolContext, PolicyType type, IPolicy<V> policy );

    /**
     * Activate all policies related to given type for correctness
     * @param type
     * @return
     */
    public ApplicationResult checkPolicy( Class<T> protocolContext, PolicyType type, V object );

    public ApplicationResult checkPolicy( Class<T> protocolContext, PolicyType type );

}
