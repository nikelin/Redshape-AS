package com.redshape.utils;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author flare
 * @author nikelin
 */
public class HasherFactory {
    private static final Logger log = Logger.getLogger( HasherFactory.class );
    private static HasherFactory defaultInstance = new HasherFactory();
    
    private Map<Class<? extends IHasher>, IHasher> hashers = new HashMap<Class<? extends IHasher>, IHasher>();
    
    public static HasherFactory getDefault() {
        return defaultInstance;
    }

    public static void setDefault( HasherFactory factory ) {
        defaultInstance = factory;
    }

    public IHasher getHasher( Class<? extends IHasher> clazz ) {
        IHasher hasher =  this.hashers.get(clazz);
        if ( hasher != null ) {
            return hasher;
        }

        try {
            this.hashers.put( clazz, hasher = clazz.newInstance() );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            return null;
        }

        return hasher;
    }

    public IHasher getHasher( String name ) {
        for( IHasher hasher : this.hashers.values() ) {
            if ( hasher.getName().equals(name) ) {
                return hasher;
            }
        }

        return null;
     }
}