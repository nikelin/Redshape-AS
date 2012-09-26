package com.redshape.net.connection.auth.credentials;

import com.redshape.net.IServer;
import com.redshape.net.connection.auth.AuthenticationException;

import java.util.Collection;

/**
 * @author nikelin
 * @date 13:08
 */
public interface IServerCredentialsProvider {
    
    public <T extends IServerCredentials> Collection<T> provide( IServer server ) throws AuthenticationException;
    
}
