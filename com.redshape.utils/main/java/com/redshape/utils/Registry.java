package com.redshape.utils;

import com.redshape.applications.IApplication;
import com.redshape.config.IConfig;
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
