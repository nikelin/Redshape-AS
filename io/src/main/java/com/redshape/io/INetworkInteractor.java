package com.redshape.io;

import com.redshape.io.net.auth.AuthenticatorException;
import com.redshape.io.net.auth.ICredentials;
import com.redshape.io.net.auth.ICredentialsProvider;
import com.redshape.utils.config.IConfig;

import java.io.IOException;

/**
 * @author nikelin
 */
public interface INetworkInteractor<T> {

    public String getServiceID();

    public void setCredentialsProvider( ICredentialsProvider provider );

    public ICredentialsProvider getCredentialsProvider();

    public void markAnonymousAllowed( boolean value );

    public boolean isAnonymousAllowed();

    public boolean isConnected() throws NetworkInteractionException;

    public void connect() throws NetworkInteractionException, AuthenticatorException;

    public void connect( ICredentials auth ) throws NetworkInteractionException, AuthenticatorException;
    
    public void close() throws NetworkInteractionException;

    // public void ping() throws NetworkInteractionException;

    // public boolean isAccessible();

    public String getProtocolId();

    public String getConnectionUri();

    public IFilesystemNode getFile( String path ) throws IOException;

    public void setConfig( IConfig config );

    public IConfig getConfig();

    public T getRawConnection();

}
