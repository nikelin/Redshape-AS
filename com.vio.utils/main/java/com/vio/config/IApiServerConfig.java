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
}
