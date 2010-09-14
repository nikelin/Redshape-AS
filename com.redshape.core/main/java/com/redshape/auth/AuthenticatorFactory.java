package com.redshape.auth;

import com.redshape.auth.adapters.AuthenticatorInterface;
import com.redshape.auth.annotations.TargetIdentity;
import com.redshape.utils.InterfacesFilter;
import com.redshape.utils.PackageLoaderException;

import java.util.HashMap;
import java.util.Map;

import com.redshape.utils.Registry;
import org.apache.log4j.Logger;

/**
 * Фабрика менеджеров аутентификации 
 *
 * @author nikelin
 */
public class AuthenticatorFactory {
	private final static Logger log = Logger.getLogger( AuthenticatorFactory.class );
    private final static AuthenticatorFactory instance = new AuthenticatorFactory();

    private Map<Class<? extends IIdentity>, AuthenticatorInterface<? extends IIdentity>> adapters = new HashMap<Class<? extends IIdentity>, AuthenticatorInterface<? extends IIdentity>>();

    public static AuthenticatorFactory getInstance() {
        return instance;
    }

    private AuthenticatorFactory() {
        this.initialize();
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
	        for ( Class<? extends AuthenticatorInterface> adapterClass : Registry.getPackagesLoader().<AuthenticatorInterface>getClasses("com.redshape.auth.adapters", new InterfacesFilter(new Class[] { AuthenticatorInterface.class }, new Class[] { TargetIdentity.class } ) ) ) {
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
