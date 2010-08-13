package com.vio.config;

import com.vio.config.readers.ConfigReaderException;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 17, 2010
 * Time: 12:50:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IApiServerConfig extends IServerConfig {

    public String getDefaultLocale() throws ConfigReaderException;

    public Integer getPresenceIdleLimit() throws ConfigReaderException;

    public List<String> getApplicationsPackages() throws ConfigReaderException;

    public List<String> getApplicationActions( String application ) throws ConfigReaderException;

    public List<String> getActionParams( String application, String action ) throws ConfigReaderException;

    public List<String> getActionParamValidators( String application, String action, String param ) throws ConfigReaderException;

    public Map<String, String> getApplications( String pkg ) throws ConfigReaderException;

    public String getSearchIndexPath() throws ConfigReaderException;

    public String getSearchEngineClass() throws ConfigReaderException;

    public List<String> getCommandsPackages() throws ConfigReaderException;

    public List<String> getFeaturesPackages() throws ConfigReaderException;

    public boolean isAnonymousRequestsAllowed( Class<?> protocolContext ) throws ConfigReaderException;

    public String getServerProtocolProvider( String serverName ) throws ConfigReaderException;

    public String getServerProtocolVersion( String serverName ) throws ConfigReaderException;

    public String getServerPolicyProtocol( String serverName, Class<?> policyClass ) throws ConfigReaderException;
    
    public String getServerPolicyProtocolVersion( String serverName, Class<?> policyClass ) throws ConfigReaderException;

    public String getServerProtocolClientsProcessor( String serverName, Class<?> protocolContext ) throws ConfigReaderException;

    public String getServerProtocolRequestsProcessor( String serverName, Class<?> protocolContext ) throws ConfigReaderException;

    public List<String> getNotificationsPackages() throws ConfigReaderException;
}
