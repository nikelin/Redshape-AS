package com.redshape.auth.adapters;

import com.redshape.auth.IIdentity;
import com.redshape.auth.storage.AuthStorage;
import com.redshape.auth.storage.MemoryStorage;
import com.redshape.utils.Constants;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 19, 2010
 * Time: 6:16:54 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class AbstractAuthenticator<T extends IIdentity> implements AuthenticatorInterface<T> {
    public static final int defaultSessionDuration = Constants.TIME_HOUR;
    private static final Class<? extends AuthStorage> defaultStorage = MemoryStorage.class;

    private AuthStorage<T> storage;

    private int sessionDuration = defaultSessionDuration;

    public AbstractAuthenticator() throws InstantiationException {
        try {
            this.setStorage( defaultStorage.newInstance() );
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }
    }

    public AbstractAuthenticator( AuthStorage storage ) {
        this.setStorage( storage );
    }

    public void setStorage( AuthStorage storage ) {
        this.storage = storage;
    }

    public AuthStorage<T> getStorage() {
        return this.storage;
    }

    public Map<Object, T> getAuthenticated() {
        return this.getStorage().getIdentities();
    }

    public void setSessionDuration( int time ) {
        this.sessionDuration = time;
    }

    public void dropSession( T user ) {
        this.getStorage().remove( user );
    }

    public T getIdentity( Object id ) {
        T user = null;
        if ( this.getStorage().isExists( id ) ) {
            user = this.getStorage().get( id );
        }

        return user;
    }

    public long getSessionDuration() {
        return this.sessionDuration;
    }

    public void unauthorize( T identity ) {
        this.getStorage().remove( identity );
    }

}
