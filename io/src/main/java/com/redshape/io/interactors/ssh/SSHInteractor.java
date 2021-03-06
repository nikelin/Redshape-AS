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

package com.redshape.io.interactors.ssh;

import com.redshape.io.IFilesystemNode;
import com.redshape.io.INetworkInteractor;
import com.redshape.utils.Commons;
import com.redshape.utils.system.processes.ISystemProcess;
import com.redshape.utils.system.scripts.IScriptExecutor;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Signal;
import net.schmizz.sshj.sftp.OpenMode;
import net.schmizz.sshj.sftp.Response;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.sftp.SFTPException;
import net.schmizz.sshj.transport.TransportException;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.io.interactors.ssh
 * @date 10/22/11 9:47 PM
 */
public class SSHInteractor implements INetworkInteractor {
	private static final Logger log = Logger.getLogger(SSHInteractor.class);
	private SSHClient connection;
	private SFTPClient sftpClient;

	public SSHInteractor(SSHClient connection) {
		if ( connection == null ) {
			throw new IllegalArgumentException("<null>");
		}

		this.connection = connection;
	}

	protected SFTPClient getSFTPClient() throws IOException {
		if ( this.sftpClient == null ) {
			this.sftpClient = connection.newSFTPClient();
		}

		return this.sftpClient;
	}

	@Override
	public void execute(IScriptExecutor executor) throws IOException {
		Session session = this.connection.startSession();

		final Session.Command command = session.exec( executor.getExecCommand() );

		executor.execute( new ISystemProcess() {
			@Override
			public String readStdError() throws IOException {
				return command.getExitErrorMessage();
			}

			@Override
			public String readStdInput() throws IOException {
				BufferedReader reader = new BufferedReader( new InputStreamReader(command.getInputStream() ) );
                String tmp;
                StringBuilder builder = new StringBuilder();
                while ( null != ( tmp = reader.readLine() ) ) {
                    builder.append(tmp);
                }

                return builder.toString();
			}

			@Override
			public InputStream getInputStream() {
				return command.getInputStream();
			}

			@Override
			public InputStream getErrorStream() {
				return command.getErrorStream();
			}

			@Override
			public OutputStream getOutputStream() {
				return command.getOutputStream();
			}

			@Override
			public boolean isSuccessful() throws IOException {
				return command.getExitStatus() == 0;
			}

			@Override
			public int waitFor() throws InterruptedException {
				return 0;
			}

			@Override
			public int exitValue() {
				return command.getExitStatus();
			}

			@Override
			public void destroy() {
				try {
					command.signal(Signal.TERM);
				} catch ( TransportException e ) {
					log.error( e.getMessage(), e );
				}
			}

			@Override
			public int getPID() {
				return command.getID();
			}
		});
	}

	@Override
	public IFilesystemNode getRoot() throws IOException {
		return this.getFile("/");
	}

	@Override
	public IFilesystemNode createFile( String path ) throws IOException {
		try {
			return new SSHFile( this.getSFTPClient(), this.getSFTPClient().open(path,
							Commons.set(OpenMode.CREAT) ) );
		} catch ( SFTPException e ) {
			log.error("SFTP exception with code " + e.getStatusCode().name() + " on requested path: " + path );
			throw e;
		}
	}

	@Override
	public IFilesystemNode getFile(String name) throws IOException {
		try {
			return new SSHFile( this.getSFTPClient(), this.getSFTPClient().open(name) );
		} catch ( SFTPException e ) {
			if ( e.getStatusCode().equals( Response.StatusCode.NO_SUCH_FILE ) ) {
				return null;
			}

			throw e;
		}
	}
}
