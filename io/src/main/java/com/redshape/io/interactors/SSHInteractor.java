package com.redshape.io.interactors;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.userauth.UserAuthException;
import org.apache.log4j.Logger;

import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;
import com.redshape.io.AbstractNetworkInteractor;
import com.redshape.io.IFilesystemNode;
import com.redshape.io.INetworkNode;
import com.redshape.io.NetworkInteractionException;
import com.redshape.io.PlatformType;
import com.redshape.io.annotations.InteractionService;
import com.redshape.io.annotations.RequiredPort;
import com.redshape.io.interactors.ssh.SSHFile;
import com.redshape.io.net.auth.IPasswordCredentials;
import com.redshape.utils.StringUtils;

import java.io.IOException;

/**
 * @author nikelin
 */
@InteractionService(
    id = "SSH",
    platforms = { PlatformType.UNIX },
    ports = {
        @RequiredPort( value = 22, protocols = {"tcp", "udp"} )
    }
)
public class SSHInteractor extends AbstractNetworkInteractor<SSHClient> {
    private static final Logger log = Logger.getLogger( SSHInteractor.class );
    public static final String SERVICE_ID = "SSH";
    public static String PROTOCOL = "ssh";
    public static int TIMEOUT = 30;

    private IConfig config;
    private SSHClient connection;
    private SFTPClient sftpClient;

    public SSHInteractor( INetworkNode node ) {
        super(PROTOCOL, node );
    }

    @Override
    public void connect() throws NetworkInteractionException {
        try {
            // Close previously opened connection if any
            this.close();

            this.connection = new SSHClient();
            this.loadVerifiedHosts();
            this.connection.setConnectTimeout( TIMEOUT );
            this.connection.connect( this.getConnectionUri(), this.getPort() );
            this.authenticate();

            this.sftpClient = this.connection.newSFTPClient();
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new NetworkInteractionException( e.getMessage() );
        }
    }

    protected void authenticate() throws NetworkInteractionException {
        assert( this.isConnected() );

        try {
            try {
                this.getConnection().loadKnownHosts();
                this.getConnection().authPublickey( this.getKeyedCredentials().getUsername() );
            } catch ( Throwable e ) {
                try {
                    IPasswordCredentials credentials = this.getPasswordCredentials();
                    this.getConnection().authPassword( credentials.getUsername(), credentials.getPassword() );
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

    public SFTPClient getSFTPClient() {
        return this.sftpClient;
    }

    @Override
    public IFilesystemNode getFile( String path ) throws IOException {
        return new SSHFile( this.getSFTPClient(), this.sftpClient.open( path ) );
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

    protected void loadVerifiedHosts() throws ConfigException {
        for ( String verifiedHash : this.getConfig().get("trusted").list() ) {
            this.getConnection().addHostKeyVerifier( verifiedHash );
        }
    }

    public SSHClient getRawConnection() {
        return this.connection;
    }

}
