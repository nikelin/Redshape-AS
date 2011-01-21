package com.redshape.daemons;

import com.redshape.config.IConfig;
import com.redshape.plugins.PluginsRegistry;
import com.redshape.plugins.loaders.PluginLoaderException;
import com.redshape.utils.Constants;
import com.redshape.utils.PackagesLoader;
import com.redshape.utils.ResourcesLoader;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Formatter;
import java.util.TimerTask;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 29, 2010
 * Time: 5:28:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class PluginsLoaderTask extends TimerTask {
    public static int PERIOD = Constants.TIME_MINUTE * 5;
    private static final Logger log = Logger.getLogger( PluginsLoaderTask.class );

    @Autowired( required = true )
	private IConfig config;
	
    @Autowired( required = true )
	private PackagesLoader packagesLoader;
    
    @Autowired( required = true )
    private ResourcesLoader resourcesLoader;
    
    public void setConfig( IConfig config ) {
    	this.config = config;
    }
    
    public IConfig getConfig() {
    	return this.config;
    }
    
    public void setPackagesLoader( PackagesLoader loader ) {
    	this.packagesLoader = loader;
    }
    
    public PackagesLoader getPackagesLoader() {
    	return this.packagesLoader;
    }
    
    public ResourcesLoader getResourcesLoader() {
    	return this.resourcesLoader;
    }
    
    public void setResourcesLoader( ResourcesLoader loader ) {
    	this.resourcesLoader = loader;
    }
    
    @Override
    public void run() {
        try {
            File pluginsDir = this.getResourcesLoader().loadFile( this.getConfig().get("paths").get("plugins").value() );

            log.info( "Plugins directory: " + this.getConfig().get("paths").get("plugins").value() );

            if ( pluginsDir.exists() ) {
                this.syncList( pluginsDir );
            } else {
                log.info("Plugins directory not exists...");
            }
        } catch( Throwable e) {
            log.error( e.getMessage(), e );
        }
    }

    protected void syncList( File directory ) {
        int count = 0;
        int total = 0;
        int errors = 0;
        
        for( String listItem : directory.list() ) {
            String path = directory.getPath() + "/" + listItem;

            try {
                if ( this.isForceUnloading(path) ) {
                    log.info("Forced unloading plugin in " + path);
                    PluginsRegistry.unload( path );
                } else if ( this.isForcedLoading(path) || !PluginsRegistry.isLoaded( path ) ) {
                    log.info("Loading plugin from " + path );
                    PluginsRegistry.load( path );

                    count++;
                }
            } catch ( PluginLoaderException e ) {
                errors++;
                log.error( "Plugin loading error...", e );   
            }

            total++;
        }

        log.info( new Formatter().format( "%d new plugins loaded... ( total - %d, errors - %d)", count, total, errors ) );
    }

    private boolean isForceUnloading( String pluginRootPath ) {
        return new File( pluginRootPath + "/.stop" ).exists();
    }

    private boolean isForcedLoading( String pluginRootPath ) {
        return new File( pluginRootPath + "/.reload").exists();
    }
}
