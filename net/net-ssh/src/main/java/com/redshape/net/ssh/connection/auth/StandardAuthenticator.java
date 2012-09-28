package com.redshape.net.ssh.connection.auth;

import com.redshape.net.IServer;
import com.redshape.net.connection.auth.AuthenticationException;
import com.redshape.net.connection.auth.credentials.IServerCredentials;
import com.redshape.net.connection.auth.credentials.IServerCredentialsProvider;
import com.redshape.net.ssh.connection.auth.credentials.AuthPairCredentials;
import com.redshape.net.ssh.connection.auth.credentials.PublicKeyCredentials;
import com.redshape.utils.IResourcesLoader;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.userauth.UserAuthException;

import java.io.IOException;

/**
 * @author nikelin
 * @date 13:49
 */
public class StandardAuthenticator implements ISshAuthenticator {
    private IConfig config;
    private IServerCredentialsProvider provider;
    private IResourcesLoader resourcesLoader;
    private boolean verifiedHostsLoaded;

    public StandardAuthenticator(IServerCredentialsProvider provider,
                                 IConfig config,
                                 IResourcesLoader resourcesLoader) {
        this.provider = provider;
        this.config = config;
        this.resourcesLoader = resourcesLoader;
    }

    protected IServerCredentialsProvider getProvider() {
        return this.provider;
    }

    protected IResourcesLoader getResourcesLoader() {
        return resourcesLoader;
    }

    protected IConfig getConfig() {
        return config;
    }

    @Override
    public void authenticate(IServer server, SSHClient connection) throws AuthenticationException {
        try {
            boolean authenticated = false;
            for (IServerCredentials credentials : this.getProvider().provide(server) ) {
                if ( (credentials instanceof PublicKeyCredentials) ) {
                    authenticated = this.authPublicKey(connection, (PublicKeyCredentials) credentials);
                } else if ( (credentials instanceof AuthPairCredentials ) ) {
                    authenticated = this.authPair(connection, (AuthPairCredentials) credentials );
                } else {
                    authenticated = this.authGeneric(connection, credentials);
                }

                if ( authenticated ) {
                    break;
                }
            }

            if ( !authenticated ) {
                throw new AuthenticationException("Authentication failed");
            }
        } catch ( ConfigException e ) {
            throw new AuthenticationException("Unable to read configuration data", e );
        } catch ( IOException e ) {
            throw new AuthenticationException("I/O related exception", e );
        }
    }

    protected boolean authGeneric( SSHClient connection, IServerCredentials credentials ) {
        return false;
    }

    protected boolean authPair( SSHClient connection, AuthPairCredentials credentials )
        throws ConfigException, IOException {
        try {
            connection.authPassword(
                credentials.getLogin(),
                credentials.getPassword()
            );
            
            return true;
        } catch ( UserAuthException e ) {
            return false;
        }
    }

    protected boolean authPublicKey( SSHClient connection, PublicKeyCredentials credentials )
        throws IOException, ConfigException {
        try {
            this.loadVerifiedHosts(connection);

            connection.authPublickey(credentials.getUsername());

            return true;
        } catch ( UserAuthException e ) {
            return false;
        }
    }
    
    protected void loadVerifiedHosts( SSHClient connection ) throws ConfigException, IOException {
        if ( verifiedHostsLoaded ) {
            return;
        }

        if ( this.getConfig() != null ) {
            for ( String verifiedHash : this.getConfig().get("trusted").list() ) {
                connection.addHostKeyVerifier(verifiedHash);
            }
        }

        connection.loadKnownHosts(
                this.getResourcesLoader().loadFile(System.getProperty("user.home") + "/.ssh/known_hosts")
        );

        verifiedHostsLoaded = true;
    }
    
}
