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

package com.redshape.utils.auth.adapters;

import com.redshape.utils.auth.AuthResult;
import com.redshape.utils.auth.AuthenticationException;
import com.redshape.utils.auth.AuthenticatorAttribute;
import com.redshape.utils.auth.IIdentity;
import com.redshape.utils.auth.storage.IAuthStorage;
import com.redshape.utils.auth.storage.MemoryStorage;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract authenticator entity
 * @author nikelin
 */
abstract public class AbstractAuthenticator<T extends IIdentity> implements IAuthenticator<T> {
    private IAuthStorage storage;
    private Map<AuthenticatorAttribute, Object> attributes = new HashMap<AuthenticatorAttribute, Object>();

    protected AbstractAuthenticator() {
        this( new MemoryStorage() );
    }

    protected AbstractAuthenticator( IAuthStorage storage )  {
        this.storage = storage;
    }

    @Override
    public T getIdentity( Object id ) {
        return this.getStorage().<T>get( id );
    }

    @Override
    public void setAttribute( AuthenticatorAttribute attribute, Object value ) {
        if ( attribute == null ) {
            throw new IllegalArgumentException("<null>");
        }

        this.attributes.put( attribute, value );
    }

    @Override
    public AuthResult authenticate( Map<String, Object> credentials )
            throws AuthenticationException {
        return this.authenticate( credentials, new HashMap<AuthenticatorAttribute, Object>() );
    }

    @Override
    public AuthResult authenticate( Map<String, Object> credentials,
                                    Map<AuthenticatorAttribute, Object> configuration )
            throws AuthenticationException {
        return this.authenticate( this.createIdentity(credentials), new HashMap<AuthenticatorAttribute, Object>() );
    }

    @Override
    public AuthResult authenticate(T identity) throws AuthenticationException {
        return this.authenticate(identity, new HashMap<AuthenticatorAttribute, Object>() );
    }

    @Override
    public void forget( T identity ) {
        this.getStorage().remove( identity );
    }

    protected IAuthStorage getStorage() {
        return this.storage;
    }

    protected <V> V getAttribute( AuthenticatorAttribute name ) {
        return (V) this.attributes.get(name);
    }

    abstract protected T createIdentity( Map<String,Object> credentials ) throws AuthenticationException;

}
