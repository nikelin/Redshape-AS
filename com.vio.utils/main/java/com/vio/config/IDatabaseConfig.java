package com.vio.config;

import com.vio.config.readers.ConfigReaderException;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 21, 2010
 * Time: 12:49:52 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IDatabaseConfig extends IConfig {

    public String getDatabaseAdapter() throws ConfigReaderException;

    public String getDatabaseUri() throws ConfigReaderException;

    public String getDatabaseUser() throws ConfigReaderException;

    public String getDatabasePassword() throws ConfigReaderException;

    public boolean isDatabaseUTF8Connection() throws ConfigReaderException;

    public String getDatabaseCharset() throws ConfigReaderException;

    public String getPersistenceUnit() throws ConfigReaderException;

    public String getMigrationsPackage() throws ConfigReaderException;

    public String getFixturesPath() throws ConfigReaderException;

    public String getDatabaseMigrationPolicy() throws ConfigReaderException;
    
}
