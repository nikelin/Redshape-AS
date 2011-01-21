package com.redshape.auth;

import com.redshape.auth.adapters.AuthenticatorInterface;
import com.redshape.auth.annotations.TargetIdentity;
import com.redshape.utils.InterfacesFilter;
import com.redshape.utils.PackageLoaderException;
import com.redshape.utils.PackagesLoader;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Фабрика менеджеров аутентификации 
 *
 * @author nikelin
 * @todo: refactoring needs
 */
public class AuthenticatorFactory {
	private final static Logger log = Logger.getLogger( AuthenticatorFactory.class );
    
	private static AuthenticatorFactory defaultInstance = new AuthenticatorFactory();

    private Map<Class<? extends IIdentity>, AuthenticatorInterface<? extends IIdentity>> adapters = new HashMap<Class<? extends IIdentity>, AuthenticatorInterface<? extends IIdentity>>();

    @Autowired( required = true )
    private PackagesLoader packagesLoader;
    
    public AuthenticatorFactory() {
        this.initialize();
    }
    
    public static void setDefault( AuthenticatorFactory instance ) {
    	defaultInstance = instance;
    }
    
    public static AuthenticatorFactory getDefault() {
    	return defaultInstance;
    }
    
    public void setPackagesLoader( PackagesLoader packagesLoader ) {
    	this.packagesLoader = packagesLoader;
    }
    
    public PackagesLoader getPackagesLoader() {
    	return this.packagesLoader;
    }

    public <T extends IIdentity> AuthenticatorInterface<T> getAdapter( Class<T> clazz ) {
        AuthenticatorInterface<T> adapter = (AuthenticatorInterface<T>) this.adapters.get(clazz);
        if ( adapter != null ) {
        	return adapter;
        }
        
        return null;
    }

    public Map<Class<? extends IIdentity>, AuthenticatorInterface<? extends IIdentity>> getAdapters() {
        return this.adapters;
    }

    public void setAdapter( Class<? extends IIdentity> clazz, AuthenticatorInterface<? extends IIdentity> adapter ) {
        this.adapters.put( clazz, adapter );
    }

    private void initialize() {
        try {
            /**
             * @FIXME: move part of logic to initialize()
             */
	        for ( Class<? extends AuthenticatorInterface> adapterClass : this.getPackagesLoader().<AuthenticatorInterface>getClasses("com.redshape.auth.adapters", new InterfacesFilter(new Class[] { AuthenticatorInterface.class }, new Class[] { TargetIdentity.class } ) ) ) {
	        	try {;
                    this.adapters.put( adapterClass.getAnnotation(TargetIdentity.class).identity(), adapterClass.newInstance() );
	        	} catch ( Throwable e ) {
	        		log.error( e.getMessage(), e );
	        	}
	        }
        } catch ( PackageLoaderException e ) {
        	log.error( e.getMessage(), e );
        }
    }

}
