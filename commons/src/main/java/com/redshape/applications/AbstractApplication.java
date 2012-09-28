package com.redshape.applications;

import com.redshape.applications.bootstrap.IBootstrap;
import com.redshape.utils.IPackagesLoader;
import com.redshape.utils.ResourcesLoader;
import com.redshape.utils.config.IConfig;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Main application abstraction
 * 
 * @author Cyril <nikelin> Karpenko <self@nikelin.ru>
 */
public abstract class AbstractApplication implements IApplication {
	private static final Logger log = Logger.getLogger( AbstractApplication.class );
    public static Map<String, String> env = new HashMap<String, String>();

	@Autowired( required = true )
    private IConfig config;

	@Autowired( required = true )
    private ResourcesLoader resourcesLoader;

	@Autowired( required = true )
    private IPackagesLoader packagesLoader;

    private boolean pidCheckup;
    
    private Integer version;

	@Autowired( required = true )
    private IBootstrap boot;

    private String[] envArgs;

    public AbstractApplication( String[] args ) throws ApplicationException {
        this.envArgs = args;

        this.initEnv(this.envArgs);
    }
    
    public void setPackagesLoader( IPackagesLoader loader ) {
    	this.packagesLoader = loader;
    }
    
    protected IPackagesLoader getPackagesLoader() {
    	return this.packagesLoader;
    }
    
    public void setResourcesLoader( ResourcesLoader loader ) {
    	this.resourcesLoader = loader;
    }
    
    public ResourcesLoader getResourcesLoader() {
    	return this.resourcesLoader;
    }

    protected void initEnv( String[] args ) throws ApplicationException {
        for ( String arg : args ) {
            if ( arg.startsWith("--") ) {
                String[] parts = arg.substring(2).split("=");
                if ( parts.length > 0 ) {
                    env.put( parts[0], parts[1] );
                }
            }
        }
    }

    public String[] getEnvArgs() {
        return this.envArgs;
    }

    public void setEnvArg( String name, String value ) {
        env.put( name, value );
    }

    public String getEnvArg( String name ) {
        return env.get(name);
    }

    public void setConfig( IConfig config ) {
    	this.config = config;
    }
    
    public IConfig getConfig() {
    	return this.config;
    }
    
    public void setBootstrap( IBootstrap bootstrap ) {
    	this.boot = bootstrap;
    }
    
    protected IBootstrap getBootstrap() {
        return this.boot;
    }

	@Override
    public void stop() {
    	log.info("Application going to be stopped");
    }

}

