package com.redshape.io.interactors;

import com.redshape.io.*;
import com.redshape.io.annotations.InteractionService;
import com.redshape.io.annotations.RequiredPort;
import com.redshape.io.interactors.ssh.SSHInteractor;
import com.redshape.io.net.auth.ICredentials;
import com.redshape.io.net.auth.IPasswordCredentials;
import com.redshape.utils.StringUtils;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.userauth.UserAuthException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author nikelin
 */
@InteractionService(
	id = ServiceID.SSH_ID,
	platforms = { PlatformType.UNIX },
	ports = {
		@RequiredPort( value = 22, protocols = {"tcp", "udp"} )
	}
)
public class SSHConnection extends AbstractNetworkConnection<SSHClient> {
	private static final Logger log = Logger.getLogger( SSHConnection.class );
	public static String PROTOCOL = "ssh";
	public static int TIMEOUT = 30;

	private IConfig config;
	private SSHClient connection;

	public SSHConnection(INetworkNode node) {
		super(PROTOCOL, node );
	}

	@Override
	public INetworkInteractor createInteractor() {
		return new SSHInteractor(this.connection);
	}

	@Override
	public void connect() throws NetworkInteractionException {
		this.connect( this.getPasswordCredentials() );
	}

	@Override
	public void connect( ICredentials credentials ) throws NetworkInteractionException {
		try {
			// Close previously opened connection if any
			this.close();

			this.connection = new SSHClient();

			this.loadVerifiedHosts();

			this.connection.setConnectTimeout( TIMEOUT );
			this.connection.connect( this.getConnectionUri(), this.getPort() );
			this.authenticate( credentials );
		} catch ( Throwable e ) {
			log.error( e.getMessage(), e );
			throw new NetworkInteractionException( e.getMessage() );
		}
	}

	protected void authenticate( ICredentials credentials ) throws NetworkInteractionException {
		assert( this.isConnected() );

		try {
			try {
				this.getConnection().authPublickey( this.getKeyedCredentials().getUsername() );
			} catch ( Throwable e ) {
				try {
					if ( ! (credentials instanceof IPasswordCredentials ) ) {
						throw new NetworkInteractionException("Wrong credentials provided");
					}

					log.info("Trying to authenticate with: " + credentials.getUsername() + "<>" + ( (IPasswordCredentials) credentials ).getPassword() );
					this.getConnection().authPassword( credentials.getUsername(), ( (IPasswordCredentials) credentials ).getPassword() );
				} catch ( UserAuthException e1 ) {
					if ( !this.isAnonymousAllowed() ) {
						log.error( e1.getMessage(), e );
						throw new NetworkInteractionException("Remote node authentication failed");
					}
				}
			}
		} catch ( TransportException e ) {
			log.error( e.getMessage() );
			throw new NetworkInteractionException( e.getMessage() );
		}
	}

	protected SSHClient getConnection() {
		return this.connection;
	}

	public boolean isConnected() {
		return this.getConnection().isConnected();
	}

	@Override
	public void close() throws NetworkInteractionException {
		try {
			if ( this.connection != null && this.connection.isConnected() ) {
				this.connection.disconnect();
			}
		} catch ( IOException e ) {
			log.error( e.getMessage(), e );
			throw new NetworkInteractionException( e.getMessage() );
		}
	}

	public String getConnectionUri() {
		return StringUtils.IPToString( this.getNetworkNode().getNetworkPoint().getAddress() );
	}

	protected void loadVerifiedHosts() throws ConfigException, IOException, FileNotFoundException {
		if ( this.getConfig() != null ) {
			for ( String verifiedHash : this.getConfig().get("trusted").list() ) {
				this.getConnection().addHostKeyVerifier( verifiedHash );
			}
		}

		this.getConnection().loadKnownHosts( new File( System.getProperty("user.home") + "/.ssh/known_hosts" ) );
	}

	@Override
	public SSHClient getRawConnection() {
		return this.connection;
	}

}
