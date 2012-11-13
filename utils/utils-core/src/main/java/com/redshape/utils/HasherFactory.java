/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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