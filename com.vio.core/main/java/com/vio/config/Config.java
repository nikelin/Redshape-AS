package com.vio.config;

import com.vio.config.readers.ConfigReaderException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 14, 2010
 * Time: 10:36:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class Config extends AbstractConfig implements IServerConfig, IApiServerConfig, IDatabaseConfig {
    private static final Logger log = Logger.getLogger( Config.class );
    public static String HIBERNATE_CONFIG_PATH = "META-INF/persistence.xml";
    public static String BOOTSTRAP_CONFIG_PATH = "configs/common/bootstrap.cfg.xml";
    public static String TEMP_PATH = "temp";

    public Config() throws ConfigReaderException, IOException {
        super( BOOTSTRAP_CONFIG_PATH );
    }

    public Config( String path ) throws ConfigReaderException, IOException {
        super(path);
    }

    public Config( File file ) throws ConfigReaderException {
        super(file);
    }

    @Override
    public String getServerHost( String serverName ) throws ConfigReaderException {
        return this.getReader().read("//servers/" + serverName + "/host");
    }

    @Override
    public int getServerPort( String serverName ) throws ConfigReaderException {
        return Integer.parseInt( this.getReader().read("//servers/" + serverName + "/port") );
    }

    @Override
    public List<String> getApplicationsPackages() throws ConfigReaderException {
        return this.getReader().readList("//applications/@package");
    }

    @Override
    public List<String> getApplicationActions( String application ) throws ConfigReaderException {
        return this.getReader().readList("//applications/" + application + "/action/@name");
    }

    @Override
    public List<String> getActionParams( String application, String action ) throws ConfigReaderException {
        return this.getReader().readList("//applications/" + application + "/action[@name='" + action + "']/params/param/@name");
    }

    @Override
    public List<String> getActionParamValidators( String application, String action, String param ) throws ConfigReaderException {
        return this.getReader().readList("//applications/" + application + "/action[@name='" + action + "']/params/param[@name='" + param + "']/validators/*");
    }

    @Override
    public Map<String, String> getApplications( String pkg ) throws ConfigReaderException {
        Map<String, String> apps = new HashMap<String, String>();

        List<String> names = this.getReader().readList("//applications[@package=" + pkg + "]/child::*");
        List<String> classes = this.getReader().readList("//applications[@package=" + pkg + "]/child::*/@class");

        for ( int i = 0; i < names.size(); i++ ) {
            apps.put( names.get(i), classes.get(i) );
        }

        return apps;
    }

    @Override
    public boolean isSSLEnabled( String serverName ) throws ConfigReaderException {
        return Boolean.parseBoolean( this.getReader().read("//servers/" + serverName + "/ssl/enabled") );
    }

    /**
     * @TODO
     * @return
     * @throws ConfigReaderException
     *
     */
    @Override
    @Deprecated
    public String getSSLStoreKey() throws ConfigReaderException {
        return this.getSSLStoreKey("api");
    }

    @Override
    public String getSSLStoreKey( String serverName) throws ConfigReaderException {
        String key = this.getReader().read("//servers/" + serverName + "/ssl/storeKey");
        if ( key == null || key.isEmpty() ) {
            key = this.getReader().read("//servers/sharedSettings/ssl/storeKey");
        }

        return key;
    }

    /**
     * @TODO
     * @return
     * @throws ConfigReaderException
     */
    @Override
    @Deprecated
    public String getSSLStoreFile() throws ConfigReaderException {
        return this.getSSLStoreFile("api");
    }

    @Override
    public String getSSLStoreFile( String serverName ) throws ConfigReaderException {
        String storeFile = this.getReader().read("//servers/" + serverName + "/ssl/storeFile");
        if ( storeFile == null || storeFile.isEmpty() ) {
            storeFile = this.getReader().read("//servers/sharedSettings/ssl/storeFile");
        }

        return storeFile;
    }

    @Override
    public int getSessionLifeTime() throws ConfigReaderException {
        return Integer.parseInt( this.getReader().read("//servers/api/sessions/lifetime") );
    }

    @Override
    public boolean isPersistSessionsAllowed() throws ConfigReaderException {
        return Boolean.parseBoolean( this.getReader().read("//servers/api/sessions/allowPersistentSessions") );
    }

    @Override
    public int getSSLServerPort( String serverName ) throws ConfigReaderException {
        return Integer.parseInt( this.getReader().read("//servers/" + serverName + "/ssl/port") );
    }

    @Override
    public String getSSLServerHost( String serverName ) throws ConfigReaderException {
        return this.getReader().read("//servers/" + serverName + "/ssl/host");
    }

    @Override
    public List<String> getActionPermissions(String application, String action) throws ConfigReaderException {
        return this.getReader().readList("//applications/" + application + "/action[@name=" + action + "]/permissions/permission/@name");
    }

    @Override
    public String getServerMode() throws ConfigReaderException {
        return this.getReader().read("//mode");
    }

    public Boolean isLoggingOn() throws ConfigReaderException {
        return Boolean.valueOf( this.getReader().read("//logging") );
    }

    @Override
    public Boolean isReconnectEnabled( String serverName ) throws ConfigReaderException {
        String value = this.getReader().read("//servers/" + serverName + "/reconnect");
        if ( value == null || value.isEmpty() ) {
            return this.isReconnectEnabled();
        }

        return Boolean.valueOf( value );
    }

    @Override
    public Boolean isReconnectEnabled() throws ConfigReaderException {
        return Boolean.valueOf( this.getReader().read("//servers/sharedSettings/reconnect") );
    }


    @Override
    public Integer getReconnectionDelay( String serverName ) throws ConfigReaderException {
        String value = this.getReader().read("//servers/" + serverName + "/reconnectDelay");
        if ( value == null || value.isEmpty() ) {
            return this.getReconnectionDelay();
        }

        return Integer.valueOf(value);
    }

    @Override
    public Integer getReconnectionDelay() throws ConfigReaderException {
        return Integer.valueOf( this.getReader().read("//servers/sharedSettings/reconnectDelay") );
    }

    @Override
    public Integer getReconnectionLimit( String serverName ) throws ConfigReaderException {
        String value = this.getReader().read("//servers/" + serverName + "/reconnectTimes");
        if ( value == null || value.isEmpty() ) {
            return this.getReconnectionLimit();
        }

        return Integer.valueOf(value);
    }

    @Override
    public Integer getReconnectionLimit() throws ConfigReaderException {
        return Integer.valueOf( this.getReader().read("//servers/sharedSettings/reconnectTimes") );
    }

    @Override
    public boolean isIpFilteringOn() throws ConfigReaderException {
        return Boolean.valueOf( this.getReader().read("//settings/security/filter-ip") );
    }

    @Override
    public List<String> getServerPolicies( String serverName ) throws ConfigReaderException {
        return this.getReader().readList("//servers/" + serverName + "/policies/*/@class");
    }

    @Override
    public String getServerPolicyType( String serverName, String policyClass ) throws ConfigReaderException {
        return this.getReader().read("//servers/" + serverName + "/policies/policy[@class='" + policyClass + "']/@type");
    }

    @Override
    public String getJMSAdapter() throws ConfigReaderException {
        return this.getReader().read("//servers/jms/adapter");
    }

    @Override
    public String getJMSUser() throws ConfigReaderException {
        return this.getReader().read("//servers/jms/user");
    }

    @Override
    public String getJMSPassword() throws ConfigReaderException {
        return this.getReader().read("//servers/jms/password");
    }

    @Override
    public String getJMSUri() throws ConfigReaderException {
        return this.getReader().read("//servers/jms/uri");
    }

    @Override
    public String getSMTPServerUser() throws ConfigReaderException {
        return this.getReader().read("//servers/smtp/user");
    }

    @Override
    public String getSMTPServerHost() throws ConfigReaderException {
        return this.getReader().read("//servers/smtp/host");
    }

    @Override
    public String getSMTPServerPort() throws ConfigReaderException {
        return this.getReader().read("//servers/smtp/port");
    }

    @Override
    public String getSenderMailPassword() throws ConfigReaderException {
        return this.getReader().read("//servers/smtp/password");
    }

    @Override
    public String getDatabaseAdapter() throws ConfigReaderException {
        return this.getReader().read("//database/adapter");
    }

    @Override
    public String getDatabaseUri() throws ConfigReaderException {
        return this.getReader().read("//database/uri");
    }

    @Override
    public String getDatabaseUser() throws ConfigReaderException {
        return this.getReader().read("//database/user");
    }

    @Override
    public String getDatabasePassword() throws ConfigReaderException {
        return this.getReader().read("//database/password");
    }

    @Override
    public boolean isDatabaseUTF8Connection() throws ConfigReaderException {
        return Boolean.parseBoolean( this.getReader().read("//database/useUTF8") );
    }

    @Override
    public String getDatabaseCharset() throws ConfigReaderException {
        return this.getReader().read("//database/charset");
    }

    public String getDefaultLocale() throws ConfigReaderException {
        return this.getReader().read("//settings/defaultLocale");
    }

    @Override
    public Integer getTaskFailsLimit() throws ConfigReaderException {
        return Integer.parseInt( this.getReader().read("//tasks/fails_limit") );
    }

    @Override
    public Integer getTaskAfterFailDelay() throws ConfigReaderException {
        return Integer.parseInt( this.getReader().read("//tasks/afterFailDelay") );
    }

    @Override
    public Integer getAccountRecoveryTime() throws ConfigReaderException {
        return Integer.parseInt( this.getReader().read("//settings/auth/recovery_time") );
    }

    @Override
    public Integer getMaxLoginFails() throws ConfigReaderException {
        return Integer.parseInt( this.getReader().read("//settings/auth/max_fails") );
    }

    @Override
    public List<String> getServersList() throws ConfigReaderException {
        return this.getReader().readNames("//servers/*");
    }

    @Override
    public String getServerClass( String serverName ) throws ConfigReaderException {
        return this.getReader().read("//servers/" + serverName + "[not( contains( child::status, 'off' ) )]/@class");
    }

    @Override
    public List<String> getRemoteServicesList( String serverName ) throws ConfigReaderException {
        return this.getReader().readList("//servers/" + serverName + "/services/service/@class");
    }

    @Override
    public Integer getPresenceIdleLimit() throws ConfigReaderException {
        return Integer.parseInt( this.getReader().read("//settings/user/presenceIdleLimit") );
    }

    @Override
    public String getMigrationsPackage() throws ConfigReaderException {
        return this.getReader().read("//migrations/@package");
    }

    @Override
    public String getFixturesPath() throws ConfigReaderException {
        return this.getReader().read("//migrations/fixturesPath");
    }

    @Override
    public String getSearchIndexPath() throws ConfigReaderException {
        return this.getReader().read("//search/indexPath");
    }

    @Override
    public String getSearchEngineClass() throws ConfigReaderException {
        return this.getReader().read("//search/engine");
    }

    @Override
    public String getPersistenceUnit() throws ConfigReaderException {
        return this.getReader().read("//database/persistenceUnit");
    }

    @Override
    public List<String> getCommandsPackages() throws ConfigReaderException {
        return this.getReader().readList("//settings/commands/package/text()");
    }

    @Override
    public String getDatabaseMigrationPolicy() throws ConfigReaderException {
        return this.getReader().read("//database/migrationPolicy");
    }

    @Override
    public List<String> getFeaturesPackages() throws ConfigReaderException {
        return this.getReader().readList("//features/package/@path");
    }

    @Override
    public String getLibrariesPath() throws ConfigReaderException {
        return this.getReader().read("//paths/path[@name='libraries']/@path");
    }

    @Override
    public boolean isAnonymousRequestsAllowed( Class<?> protocolContext ) throws ConfigReaderException {
        return Boolean.valueOf( this.getReader().read("//servers/sharedSettings/security/protocols/" + protocolContext.getName() + "/isAnonymousRequestsAllowed") );
    }

    @Override
    public String getServerProtocolProvider( String serverName ) throws ConfigReaderException {
        return this.getReader().read("//servers/" + serverName + "/protocol/providerClass");
    }

    @Override
    public String getServerProtocolVersion( String serverName ) throws ConfigReaderException {
        return this.getReader().read("//servers/" + serverName + "/protocol/version");
    }

    @Override
    public String getServerPolicyProtocol( String serverName, Class<?> policyClass ) throws ConfigReaderException {
        return this.getReader().read("//servers/" + serverName + "/policies/policy[@class='" + policyClass.getCanonicalName() + "']/protocolClass");
    }

    @Override
    public String getServerPolicyProtocolVersion( String serverName, Class<?> policyClass ) throws ConfigReaderException {
        return this.getReader().read("//servers/" + serverName + "/policies[@class='" + policyClass.getCanonicalName() + "']/protocolVersion");
    }

    @Override
    public String getServerProtocolClientsProcessor( String serverName, Class<?> protocolContext ) throws ConfigReaderException {
        return this.getReader().read("//servers/" + serverName + "/protocol/clientsProcessor");
    }

    @Override
    public String getServerProtocolRequestsProcessor( String serverName, Class<?> protocolContext ) throws ConfigReaderException {
        return this.getReader().read("//servers/" + serverName + "/protocol/requestsProcessor");
    }

}
