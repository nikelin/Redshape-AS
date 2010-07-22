package com.vio.utils;

import com.vio.applications.IApplication;
import com.vio.config.IApiServerConfig;
import com.vio.config.IConfig;
import com.vio.config.IDatabaseConfig;
import com.vio.config.IServerConfig;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Timer;


/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 18, 2010
 * Time: 2:45:42 PM
 * To change this template use File | Settings | File Templates.
 */
public final class Registry {
    private static final Logger log = Logger.getLogger( Registry.class );

    private static String rootDirectory;
    private static String resourcesDirectory;
    private static ResourcesLoader resourcesLoader = new ResourcesLoader();
    private static PackageLoader packagesLoader = new PackageLoader();
    private static IApplication application;
    private static IConfig config;
    private static IServerConfig serverConfig;
    private static IApiServerConfig apiServerConfig;
    private static IDatabaseConfig databaseConfig;
    private static Timer ticker = new Timer();

    public static void setApplication( IApplication instance ) {
        application = instance;
    }

    public static IApplication getApplication() {
        return application;
    }

    public static void setResourcesDirectory( String path ) {
        resourcesDirectory = path;
    }

    public static String getResourcesDirectory() {
        return resourcesDirectory;
    }

    public static String getRootDirectory() {
        return rootDirectory;
    }

    public static void setRootDirectory( String path ) {
        rootDirectory = path;
    }

    public static String[] getClasspathEntries() {
        return System.getProperty("java.class.path").split( File.pathSeparator );
    }

    public static void addClasspathEntry( String entry ) {
        System.setProperty( "java.class.path", System.getProperty("java.class.path") + File.pathSeparator + entry );
    }

    public static void setResourcesLoader( ResourcesLoader loader ) {
        resourcesLoader = loader;
    }

    public static ResourcesLoader getResourcesLoader() {
        return resourcesLoader;
    }

    public static void setPackagesLoader( PackageLoader loader ) {
        packagesLoader = loader;
    }

    public static PackageLoader getPackagesLoader() {
        return packagesLoader;
    }

    public static void setServerConfig( IServerConfig config ) {
        serverConfig = config;
    }

    public static IServerConfig getServerConfig() {
        if ( serverConfig !=null ) {
            return serverConfig;
        }

        return IServerConfig.class.isAssignableFrom( config.getClass() ) ? (IServerConfig) config : null;
    }

    public static void setApiServerConfig( IApiServerConfig config ) {
        apiServerConfig = config;
    }

    public static IApiServerConfig getApiServerConfig() {
        if ( apiServerConfig != null ) {
            return apiServerConfig;
        }

        return IApiServerConfig.class.isAssignableFrom( config.getClass() ) ? (IApiServerConfig) config : null;
    }

    public static void processLibrariesPath( String path ) throws IOException {
        if ( path.contains(":") ) {
            for ( String pathPart : path.split(":") ) {
                loadLibraries(pathPart);
            }
        } else {
            loadLibraries( path );
        }
    }

    public static void loadLibraries( String path ) throws IOException {
        log.info("Processing libraries from " + path );
        File file = new File(path);
        if ( !file.exists() ) {
            throw new IOException("Library does not exists by given path!");
        }

        for( String entry : file.list() ) {
            if ( entry.endsWith("jar") ) {
                addClasspathEntry( new File( file.getAbsolutePath() + File.separator + entry ).getAbsolutePath() );
            }
        }
    }

    public static void setDatabaseConfig( IDatabaseConfig config ) {
        databaseConfig = config;
    }

    public static IDatabaseConfig getDatabaseConfig() {
        if ( databaseConfig != null ) {
            return databaseConfig;
        }

        return IDatabaseConfig.class.isAssignableFrom( config.getClass() ) ? (IDatabaseConfig) config : null; 
    }

    public static void setConfig( IConfig config ) {
        Registry.config = config;
    }

    public static IConfig getConfig() {
        return Registry.config;
    }

    public static void setTicker( Timer timer ) {
        ticker = timer;
    }

    public static Timer getTicker() {
        return ticker;
    }
}
