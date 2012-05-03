package com.redshape.utils.auth.adapters;

import com.redshape.utils.auth.AuthResult;
import com.redshape.utils.auth.AuthenticationException;
import com.redshape.utils.auth.AuthenticatorAttribute;
import com.redshape.utils.auth.IIdentity;

import java.util.Map;

/**
 * Interface for entities responsible for providing authentication
 * logic.
 *
 * @param <T>
 * @author nikelin
 */
public interface IAuthenticator<T extends IIdentity> {

    /**
     * Set new attribute on current entity.
     *
     * @param name
     * @param value
     */
    public void setAttribute( AuthenticatorAttribute name, Object value );

    /**
     * Check is that entity authentication session not expired
     *
     * @param identity
     * @return
     */
    public boolean isAuthExpired( T identity );

    /**
     * Proceed given entity authentication
     * @param identity
     * @return
     * @throws AuthenticationException
     */
    public AuthResult authenticate( T identity ) throws AuthenticationException;

    /**
     * Proceed authentication based on given authority credentials
     *
     * @param credentials
     * @return
     */
    public AuthResult authenticate( Map<String, Object> credentials )
            throws AuthenticationException;

    /**
     * Proceed authentication based on given authority credentials
     *
     * @param credentials
     * @return
     */
    public AuthResult authenticate( Map<String, Object> credentials,
                                    Map<AuthenticatorAttribute, Object> configuration )
        throws AuthenticationException;

    /**
     * Proceed given entity authentication with respect to provided
     * configuration options, which will have higher priority comparing to global attributes
     * of authenticator provided through setAttribute(*,*) method.
     *
     * @param identity
     * @return AuthResult
     */
    public AuthResult authenticate( T identity, Map<AuthenticatorAttribute, Object> configuration ) throws AuthenticationException;

    /**
     * Get entity by given ID from related storage.
     *
     * Null will be returned if entity not presents in authenticated entities
     * storage.
     *
     * @deprecated
     * @param id
     * @return Identity
     */
    public T getIdentity( Object id );

    /**
     * Remove given entity from authenticated entities store.
     *
     * @param identity
     * @return AuthenticatorInterface
     */
    public void forget( T identity );

}
