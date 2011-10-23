package com.redshape.io.interactors;

import com.redshape.io.*;
import com.redshape.io.annotations.InteractionService;
import com.redshape.io.annotations.RequiredPort;
import com.redshape.io.interactors.samba.SambaAuthenticator;
import com.redshape.io.interactors.samba.SambaFile;
import com.redshape.io.interactors.samba.SambaInteractor;
import com.redshape.io.net.auth.AuthenticatorException;
import com.redshape.io.net.auth.ICredentials;
import com.redshape.io.net.auth.impl.samba.NtlmCredentials;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import org.apache.log4j.Logger;

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
    id = ServiceID.SAMBA_ID,
    platforms = { PlatformType.UNIX, PlatformType.WINNT },
    ports = {
        @RequiredPort( value = 445, protocols = {"tcp", "udp"} ),
        @RequiredPort( value = 138, protocols = {"tcp", "udp"} ),
        @RequiredPort( value = 137, protocols = {"tcp", "udp"} )
    }
)
public class SambaConnection extends AbstractNetworkConnection<SmbFile> {
    private static final Logger log = Logger.getLogger( SambaConnection.class );
    private static String PROTOCOL = "smb";
    
    private SmbFile connection;
    private SambaAuthenticator authenticator;

    public SambaConnection(INetworkNode node) {
        super(PROTOCOL, node );
    }

	@Override
	public INetworkInteractor createInteractor() {
		return new SambaInteractor(this);
	}

	@Override
    public boolean isConnected() throws NetworkInteractionException {
        try {
            return this.getConnection().exists();
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new NetworkInteractionException( e.getMessage() );
        }
    }

	@Override
    public void connect() throws NetworkInteractionException {
    	this.connect(null);
    }

    synchronized public SmbFile getConnection( ICredentials credentials ) throws AuthenticatorException,
																			MalformedURLException,
																			NetworkInteractionException {
        if ( this.connection == null ) {
            this.connection = this._createConnection(credentials);
        }

        return this.connection;
    }
    
    synchronized protected SmbFile getConnection() throws AuthenticatorException,
														MalformedURLException,
														NetworkInteractionException {
    	return this.getConnection( this.getPasswordCredentials() );
    }

    synchronized private SmbFile _createConnection( ICredentials credentials ) throws AuthenticatorException,
                                                            				MalformedURLException,
																			NetworkInteractionException {
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

	@Override
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

	@Override
    public SmbFile getRawConnection() {
        return this.connection;
    }

	@Override
	public void connect(ICredentials auth) throws NetworkInteractionException,
			AuthenticatorException {
        try {
            this.getConnection(auth).connect();
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

}
