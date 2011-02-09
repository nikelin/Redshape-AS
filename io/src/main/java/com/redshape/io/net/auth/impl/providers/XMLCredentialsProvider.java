package com.redshape.io.net.auth.impl.providers;

import org.apache.log4j.Logger;

import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;
import com.redshape.utils.config.XMLConfig;
import com.redshape.io.net.auth.AbstractCredentialsProvider;
import com.redshape.io.net.auth.ICredentials;
import com.redshape.utils.ReflectionUtils;
import com.redshape.utils.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Implementation of credentials provider which read its state from XML-based configuration
 * file.
 *
 * @author nikelin
 */
public class XMLCredentialsProvider extends AbstractCredentialsProvider {
    private static final Logger log = Logger.getLogger( XMLCredentialsProvider.class );
    
    private XMLConfig config;
    private boolean initialized;

    public XMLCredentialsProvider( XMLConfig config ) throws ConfigException {
        this.config = config;

        this.init();
    }

    synchronized public void init() throws ConfigException {
        assert( !this.isInitialized() );

        IConfig config = this.getConfig();
        for ( IConfig dataNode : config.get("nodes").childs() ) {
            try {
                this.addCredentials(
                    InetAddress.getByAddress( StringUtils.stringToIP( dataNode.get("connection").get("address").value() ) ),
                    this._processNodeConfig( dataNode )
                );
            } catch ( UnknownHostException e ) {
                log.error( e.getMessage(), e );
                throw new ConfigException( e.getMessage() );
            }
        }

        this.initialized = true;
    }

    private ICredentials _processNodeConfig( IConfig nodeConfig ) throws ConfigException {
        try {
            Class<? extends ICredentials> credentialsClazz =
                            ( Class<? extends ICredentials> ) Class.forName(
                                        nodeConfig.get("class").value()
                            );

            String[] arguments = nodeConfig.get("data").list();

            return credentialsClazz.getConstructor( ReflectionUtils.getTypesList( arguments ) )
                                   .newInstance( arguments );
        } catch ( ConfigException e ) {
            throw e;
        } catch ( Throwable e ) {
            throw new ConfigException();
        }
    }

    protected IConfig getConfig() {
        return this.config;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

}
