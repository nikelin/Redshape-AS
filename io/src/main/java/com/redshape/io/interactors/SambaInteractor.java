package com.redshape.io.interactors;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;

import org.apache.log4j.Logger;

import com.redshape.io.AbstractNetworkInteractor;
import com.redshape.io.IFilesystemNode;
import com.redshape.io.INetworkNode;
import com.redshape.io.NetworkInteractionException;
import com.redshape.io.PlatformType;
import com.redshape.io.annotations.InteractionService;
import com.redshape.io.annotations.RequiredPort;
import com.redshape.io.interactors.samba.SambaAuthenticator;
import com.redshape.io.interactors.samba.SambaFile;
import com.redshape.io.net.auth.AuthenticatorException;
import com.redshape.io.net.auth.IPasswordCredentials;
import com.redshape.io.net.auth.impl.samba.NtlmCredentials;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 11/3/10
 * Time: 3:24 PM
 * To change this template use File | Settings | File Templates.
 */
@InteractionService(
    id = "SAMBA",
    platforms = { PlatformType.UNIX, PlatformType.WINNT },
    ports = {
        @RequiredPort( value = 445, protocols = {"tcp", "udp"} ),
        @RequiredPort( value = 138, protocols = {"tcp", "udp"} ),
        @RequiredPort( value = 137, protocols = {"tcp", "udp"} )
    }
)
public class SambaInteractor extends AbstractNetworkInteractor<SmbFile> {
    private static final Logger log = Logger.getLogger( SambaInteractor.class );
    private static String PROTOCOL = "smb";
    public static final String SERVICE_ID = "SAMBA";
    
    private SmbFile connection;
    private SambaAuthenticator authenticator;

    public SambaInteractor( INetworkNode node ) {
        super(PROTOCOL, node );
    }

    @Override
    // @todo: rework. not stable condition
    public boolean isConnected() throws NetworkInteractionException {
        try {
            return this.getConnection().exists();
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new NetworkInteractionException( e.getMessage() );
        }
    }

    public void connect() throws NetworkInteractionException {
        try {
            this.getConnection().connect();
        } catch ( Throwable e ) {
            NetworkInteractionException exception;
            if ( this.getAuthenticator().isAuthException() ) {
                exception = new AuthenticatorException( this.getAuthenticator().getAuthException().getMessage() );
            } else {
                exception = new NetworkInteractionException();
            }

            log.error( exception.getMessage(), e );
            throw exception;
        }
    }

    synchronized protected SmbFile getConnection() throws AuthenticatorException, MalformedURLException {
        if ( this.connection == null ) {
            this.connection = this._createConnection();
        }

        return this.connection;
    }

    synchronized private SmbFile _createConnection() throws AuthenticatorException,
                                                            MalformedURLException {
        IPasswordCredentials credentials = this.getPasswordCredentials();
        if ( credentials == null  ) {
            if ( !this.isAnonymousAllowed() ) {
                throw new AuthenticatorException("Credentials not found");
            }

            credentials = this.createAnonymousCredentials();
        } else if ( !NtlmCredentials.class.isAssignableFrom( credentials.getClass() ) ) {
            throw new AuthenticatorException("Wrong type of credentials given (must extends from NtlmCredentials) !");
        }

        return new SmbFile(
            this.getConnectionUri(),
            this.getAuthenticator().createNtlmPasswordAuthentication( (NtlmCredentials) credentials )
        );
    }

    protected NtlmCredentials createAnonymousCredentials() {
        return new NtlmCredentials( new NtlmPasswordAuthentication( "?", "GUEST", "" ) );
    }

    public void setAuthenticator( SambaAuthenticator authenticator ) {
        this.authenticator = authenticator;
    }

    protected SambaAuthenticator getAuthenticator() {
        return this.authenticator;
    }

    public void close() throws NetworkInteractionException {
        log.info("Not implemented");
    }

    public IFilesystemNode getFile( String path ) throws IOException {
        try {
            return new SambaFile( new SmbFile( this.getConnection(), path ) );
        } catch ( Throwable e ) {
            throw new NetworkInteractionException();
        }
    }

    public SmbFile getRawConnection() {
        return this.connection;
    }

}
