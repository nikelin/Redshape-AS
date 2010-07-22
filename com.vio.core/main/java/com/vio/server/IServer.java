package com.vio.server;

import com.vio.exceptions.ExceptionWithCode;
import com.vio.io.protocols.readers.IRequestReader;
import com.vio.io.protocols.response.IResponse;
import com.vio.io.protocols.writers.IResponseWriter;
import com.vio.server.policy.IPolicy;
import com.vio.server.policy.PolicyType;

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
public interface IServer<T extends IResponse> {

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
     * Changes reading method for requests on given server
     * @param reader
     */
    public void setReader( IRequestReader reader );

    /**
     * Return current reading method
     * @return
     */
    public IRequestReader getReader();

    /**
     * Changes writing method for responses from given server
     * @param writer
     */
    public void setWriter( IResponseWriter writer );

    /**
     * Returns responses writer for given server
     * @return
     */
    public IResponseWriter getWriter();

    /**
     * Get all policies related given type
     * @param type
     * @return
     */
    public Collection<IPolicy> getPolicies( PolicyType type );

    /**
     * Add new server policy
     * @param type Category what policy related on
     * @param policy Policy
     */
    public void addPolicy( PolicyType type, IPolicy policy );

    /**
     * Activate all policies related to given type for correctness
     * @param type
     * @return
     */
    public boolean checkPolicy( PolicyType type, Object object ) throws ExceptionWithCode;

    public boolean checkPolicy( PolicyType type ) throws ExceptionWithCode;
}
