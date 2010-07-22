package com.vio.server.adapters.socket.server;

import com.vio.config.IServerConfig;
import com.vio.server.ServerException;
import com.vio.utils.Registry;
import org.apache.log4j.Logger;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.security.KeyStore;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 4, 2010
 * Time: 7:25:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class SSLServerSocketAdapter extends ServerSocketAdapter {
    private static final Logger log = Logger.getLogger( SSLServerSocketAdapter.class );

    public SSLServerSocketAdapter(String host, Integer port ) throws IOException, ServerException {
        super(host, port);
    }

    @Override
    public void init() throws ServerException {
        try {
            SSLServerSocket sslSocket = (SSLServerSocket) SSLServerSocketFactory.getDefault().createServerSocket();

            log.info("Started in SSL mode...");
            try {
                this.initSSL();
            } catch ( Throwable e ) {
                log.error( e.getMessage(), e );
                throw new IOException();
            }

            sslSocket.setEnabledProtocols( sslSocket.getSupportedProtocols());
            sslSocket.setEnabledCipherSuites( sslSocket.getSupportedCipherSuites() );

            this.setSocket(sslSocket);
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ServerException();
        }
    }

    protected void initSSL() throws Throwable {
        IServerConfig config = (IServerConfig) Registry.getConfig();
        SSLContext context = SSLContext.getInstance("TLS");
        // The reference implementation only supports X.509 keys
        KeyManagerFactory kmf =
        KeyManagerFactory.getInstance("SunX509");
        // Sun's default kind of key store
        KeyStore ks = KeyStore.getInstance("JKS");
        // For security, every key store is encrypted with a
        // pass phrase that must be provided before we can load
        // it from disk. The pass phrase is stored as a char[] array
        // so it can be wiped from memory quickly rather than
        // waiting for a garbage collector. Of course using a string
        // literal here completely defeats that purpose.
        char[] password = config.getSSLStoreKey().toCharArray( );

        ks.load( new FileInputStream( Registry.getResourcesLoader().loadFile( config.getPath("security") + "/" + config.getSSLStoreFile() ) ), password);
        kmf.init(ks, password);

        //
        context.init(kmf.getKeyManagers(  ), null, null);
    }

    protected ServerSocket createServerSocket() throws IOException {
        return SSLServerSocketFactory.getDefault().createServerSocket();
    }

}
