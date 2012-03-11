package com.redshape.net.ssh.connection.capabilities;

import com.redshape.net.connection.ConnectionException;
import com.redshape.net.connection.capabilities.IConsoleCapabilitySupport;
import com.redshape.net.ssh.connection.SshConnectionSupport;
import com.redshape.utils.system.processes.ISystemProcess;
import com.redshape.utils.system.scripts.IScriptExecutor;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Signal;
import net.schmizz.sshj.transport.TransportException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author nikelin
 * @date 16:35
 */
public class ConsoleCapabilitySupport implements IConsoleCapabilitySupport {

    public class ProcessAdapter implements ISystemProcess {
        private Session.Command command;

        public ProcessAdapter(Session.Command command) {
            this.command = command;
        }

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
                throw new RuntimeException( e.getMessage(), e );
            }
        }

        @Override
        public int getPID() {
            return command.getID();
        }
    }

    private SshConnectionSupport connection;

    public ConsoleCapabilitySupport( SshConnectionSupport connection ) {
        this.connection = connection;
    }

    protected SshConnectionSupport getConnection() {
        return connection;
    }

    @Override
    public void execute(IScriptExecutor executor) throws ConnectionException {
        try {
            Session session = this.getConnection()
                                  .asRawConnection()
                                    .startSession();

            executor.execute(
                new ProcessAdapter(
                    session.exec( executor.getExecCommand() )
                )
            );
        } catch ( IOException e ) {
            throw new ConnectionException( e.getMessage(), e );
        }
    }

    @Override
    public boolean isAvailable() {
        return true;
    }
}
