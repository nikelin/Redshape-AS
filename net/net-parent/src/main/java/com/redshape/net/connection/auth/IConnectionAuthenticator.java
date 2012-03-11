package com.redshape.net.connection.auth;

import com.redshape.net.IServer;

/**
 * @author nikelin
 * @date 13:12
 */
public interface IConnectionAuthenticator<T> {

    public void authenticate(IServer server, T connection) throws AuthenticationException;

}
