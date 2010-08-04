package com.vio.config;

import com.vio.config.readers.ConfigReaderException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 17, 2010
 * Time: 12:38:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IServerConfig extends IConfig {
    
    public String getServerHost( String serverName ) throws ConfigReaderException;

    public int getServerPort( String serverName ) throws ConfigReaderException;

    public boolean isSSLEnabled( String serverName ) throws ConfigReaderException;

    @Deprecated
    public String getSSLStoreKey() throws ConfigReaderException;

    public String getSSLStoreKey( String serverName) throws ConfigReaderException;

    @Deprecated
    public String getSSLStoreFile() throws ConfigReaderException;

    public String getSSLStoreFile( String serverName ) throws ConfigReaderException;

    public int getSessionLifeTime() throws ConfigReaderException;

    public boolean isPersistSessionsAllowed() throws ConfigReaderException;

    public int getSSLServerPort( String serverName ) throws ConfigReaderException;

    public String getSSLServerHost( String serverName ) throws ConfigReaderException;

    public List<String> getActionPermissions(String application, String action) throws ConfigReaderException;

    public String getServerMode() throws ConfigReaderException;

    public Boolean isReconnectEnabled( String serverName ) throws ConfigReaderException;

    public Boolean isReconnectEnabled() throws ConfigReaderException;

    public Integer getReconnectionDelay( String serverName ) throws ConfigReaderException;

    public Integer getReconnectionDelay() throws ConfigReaderException;

    public Integer getReconnectionLimit( String serverName ) throws ConfigReaderException;

    public Integer getReconnectionLimit() throws ConfigReaderException;

    public boolean isIpFilteringOn() throws ConfigReaderException;

    public String getJMSAdapter() throws ConfigReaderException;

    public String getJMSUser() throws ConfigReaderException;

    public String getJMSPassword() throws ConfigReaderException;

    public String getJMSUri() throws ConfigReaderException;

    public String getSMTPServerHost() throws ConfigReaderException;

    public String getSMTPServerPort() throws ConfigReaderException;

    public String getSMTPServerUser() throws ConfigReaderException;

    public String getSenderMailPassword() throws ConfigReaderException;

    public Integer getTaskFailsLimit() throws ConfigReaderException;

    public Integer getTaskAfterFailDelay() throws ConfigReaderException;

    public Integer getAccountRecoveryTime() throws ConfigReaderException;

    public Integer getMaxLoginFails() throws ConfigReaderException;

    public List<String> getServersList() throws ConfigReaderException;

    public String getServerClass( String serverName ) throws ConfigReaderException;

    public List<String> getServerPolicies( String serverName ) throws ConfigReaderException;

    public String getServerPolicyType( String serverName, String policyType ) throws ConfigReaderException;

    public List<String> getRemoteServicesList( String serverName ) throws ConfigReaderException;

    public String getLibrariesPath() throws ConfigReaderException;
    
}
