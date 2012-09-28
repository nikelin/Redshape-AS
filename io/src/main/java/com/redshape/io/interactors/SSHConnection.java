package com.redshape.io.interactors;

import com.redshape.io.*;
import com.redshape.io.annotations.InteractionService;
import com.redshape.io.annotations.RequiredPort;
import com.redshape.io.interactors.ssh.SSHInteractor;
import com.redshape.io.net.auth.ICredentials;
import com.redshape.io.net.auth.IKeyedCredentials;
import com.redshape.io.net.auth.IPasswordCredentials;
import com.redshape.utils.SimpleStringUtils;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.userauth.UserAuthException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

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
		this.connect( this.getCredentialsProvider().getCredentials(this.getNode().getNetworkPoint()) );
	}

	@Override
	public void connect( Collection<ICredentials> credentials ) throws NetworkInteractionException {
		try {
			// Close previously opened connection if any
			if ( this.isConnected() ) {
				this.close();
			}

			this.connection = new SSHClient();
			this.loadVerifiedHosts();
			this.connection.setConnectTimeout( TIMEOUT );
			this.connection.connect( this.getConnectionUri(), 22 );
			this.authenticate( credentials );
		} catch ( Throwable e ) {
			log.error( e.getMessage(), e );
			throw new NetworkInteractionException( e.getMessage() );
		}
	}

	protected void authenticate( Collection<ICredentials> credentials )
			throws NetworkInteractionException {
		for ( ICredentials credentialsData : credentials ) {
			try {
				try {
					if ( credentialsData instanceof IKeyedCredentials ) {
						this.getConnection().authPublickey( credentialsData.getUsername() );
					} else {
						final IPasswordCredentials passwordCredentials = (IPasswordCredentials) credentialsData;
						log.info("Trying to authenticate with: "
								+ passwordCredentials.getUsername() + "<>"
								+ passwordCredentials.getPassword() );
						this.getConnection().authPassword(
								passwordCredentials.getUsername(),
								passwordCredentials.getPassword()
						);
					}
				} catch ( UserAuthException e ) {
					if ( !this.isAnonymousAllowed() ) {
						log.error( e.getMessage(), e );
						throw new NetworkInteractionException("Remote node authentication failed");
					}
				}
			} catch ( TransportException e ) {
				log.error( e.getMessage() );
				throw new NetworkInteractionException( e.getMessage() );
			}
		}
	}

	protected SSHClient getConnection() {
		return this.connection;
	}

	public boolean isConnected() {
		return null != this.getConnection() && this.getConnection().isConnected();
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
		return SimpleStringUtils.IPToString(this.getNode().getNetworkPoint().getAddress());
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

	@Override
	public String getConnectionUri( INetworkNode node ) {
		return new StringBuilder().append("ssh://")
				.append( SimpleStringUtils.IPToString(node.getNetworkPoint().getAddress()) )
				.append( ":22")
				.toString();
	}

}
