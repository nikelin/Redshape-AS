package com.redshape.io.interactors.ssh;

import com.redshape.io.IFilesystemNode;
import com.redshape.io.INetworkInteractor;
import com.redshape.utils.system.processes.ISystemProcess;
import com.redshape.utils.system.scripts.IScriptExecutor;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Signal;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.TransportException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
				return command.getErrorAsString();
			}

			@Override
			public String readStdInput() throws IOException {
				return command.getOutputAsString();
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
	public IFilesystemNode getFile(String name) throws IOException {
		return new SSHFile( this.getSFTPClient(), this.getSFTPClient().open(name) );
	}
}
