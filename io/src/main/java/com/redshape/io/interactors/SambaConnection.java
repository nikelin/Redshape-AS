/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
import com.redshape.utils.SimpleStringUtils;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;

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
		try {
			this.getConnection(
				this.getCredentialsProvider()
					.getCredentials(this.getNode().getNetworkPoint())
			);
		} catch ( MalformedURLException e ) {
			throw new NetworkInteractionException( e.getMessage(), e );
		}
	}

	@Override
	public void connect( Collection<ICredentials> auth) throws NetworkInteractionException,
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

	synchronized protected SmbFile getConnection( Collection<ICredentials> credentials ) throws AuthenticatorException,
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
		return this.getConnection( this.getCredentialsProvider().getCredentials(
				this.getNode().getNetworkPoint() ) );
	}

	synchronized private SmbFile _createConnection( Collection<ICredentials> credentials )
			throws NetworkInteractionException {

		SmbFile result = null;
		for ( ICredentials credentialsData : credentials ) {
			if ( !( credentialsData instanceof NtlmCredentials ) ) {
				continue;
			}

			try {
				result = new SmbFile(
					this.getConnectionUri( this.getNode() ),
					this.getAuthenticator().createNtlmPasswordAuthentication(
						(NtlmCredentials)credentialsData )
				);
			} catch ( Throwable e ) {
				log.error( e.getMessage(), e );
			}
		}

		if ( result == null ) {
			throw new NetworkInteractionException("Unable to establish network connection");
		}

		return result;
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
	public String getConnectionUri( INetworkNode node ) {
		return new StringBuilder().append("smb://")
			.append( SimpleStringUtils.IPToString(node.getNetworkPoint().getAddress()) )
			.append( ":445")
		.toString();
	}
}
