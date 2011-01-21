package com.redshape.io;

import java.util.Collection;

import com.redshape.config.IConfig;
import com.redshape.io.annotations.InteractionService;
import com.redshape.io.net.auth.ICredentials;
import com.redshape.io.net.auth.ICredentialsProvider;
import com.redshape.io.net.auth.IKeyedCredentials;
import com.redshape.io.net.auth.IPasswordCredentials;
import com.redshape.utils.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 11/3/10
 * Time: 5:07 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractNetworkInteractor<T>
                implements INetworkInteractor<T> {
    private INetworkNode interactableNode;
    private String protocolId;
    private boolean isAnonymousAllowed;
    private ICredentialsProvider credentialsProvider;
    private IConfig config;

    public AbstractNetworkInteractor( String protocolId, INetworkNode node ) {
        this.protocolId = protocolId;
        this.interactableNode = node;
    }

    public void setConfig( IConfig config ) {
        this.config = config;
    }

    public IConfig getConfig() {
        return this.config;
    }

    public void markAnonymousAllowed( boolean value ) {
        this.isAnonymousAllowed = value;
    }

    public boolean isAnonymousAllowed() {
        return this.isAnonymousAllowed;
    }

    @Override
    public String getProtocolId() {
        return this.protocolId;
    }

    protected INetworkNode getNetworkNode() {
        return this.interactableNode;
    }

    public void setCredentialsProvider( ICredentialsProvider provider ) {
        this.credentialsProvider = provider;
    }

    public ICredentialsProvider getCredentialsProvider() {
        return this.credentialsProvider;
    }

    protected Collection<ICredentials> getCredentials() {
        assert( this.getCredentialsProvider() != null );

        return this.getCredentialsProvider().getCredentials(
            this.getNetworkNode().getNetworkPoint(),
            this.getServiceID()
        );
    }

    protected IKeyedCredentials getKeyedCredentials() {
        for ( ICredentials credentials : this.getCredentials() ) {
            if ( credentials instanceof IKeyedCredentials ) {
                return (IKeyedCredentials) credentials;
            }
        }

        return null;
    }

    protected IPasswordCredentials getPasswordCredentials() {
        for ( ICredentials credentials : this.getCredentials() ) {
            if ( credentials instanceof IPasswordCredentials ) {
                return (IPasswordCredentials) credentials;
            }
        }

        return null;
    }

    public String getServiceID() {
        return this.getClass().getAnnotation( InteractionService.class ).id();
    }

    protected Integer getPort() {
        return this.getClass().getAnnotation( InteractionService.class ).ports()[0].value();
    }

    @Override
    public String getConnectionUri() {
        return new StringBuilder().append( this.getProtocolId() )
                           .append("://")
                           .append( StringUtils.IPToString(this.getNetworkNode().getNetworkPoint().getAddress()) )
                           .append( ":" )
                           .append( this.getPort() )
                           .toString();
    }

}
