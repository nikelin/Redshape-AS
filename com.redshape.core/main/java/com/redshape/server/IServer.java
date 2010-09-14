package com.redshape.server;

import com.redshape.exceptions.ExceptionWithCode;
import com.redshape.io.protocols.core.IProtocol;
import com.redshape.server.policy.IPolicy;
import com.redshape.server.policy.PolicyType;

import java.io.IOException;
import java.util.Collection;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.server
 * @date May 4, 2010
 */
public interface IServer {

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
    public Collection<IPolicy> getPolicies( Class<? extends IProtocol> context, PolicyType type );

    /**
     * Add new server policy
     * @param type Category what policy related on
     * @param policy Policy
     */
    public void addPolicy( Class<? extends IProtocol> protocolContext, PolicyType type, IPolicy policy );

    /**
     * Activate all policies related to given type for correctness
     * @param type
     * @return
     */
    public boolean checkPolicy( Class<? extends IProtocol> protocolContext, PolicyType type, Object object ) throws ExceptionWithCode;

    public boolean checkPolicy( Class<? extends IProtocol> protocolContext, PolicyType type ) throws ExceptionWithCode;

}
