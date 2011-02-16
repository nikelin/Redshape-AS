package com.redshape.server.execution;

import com.redshape.io.server.policy.ApplicationResult;
import com.redshape.io.server.policy.PolicyType;
import com.redshape.server.ISocketServer;
import org.apache.log4j.Logger;

import com.redshape.io.net.adapters.socket.client.ISocketAdapter;
import com.redshape.server.processors.connection.IClientsProcessor;

public class ServerExecutionThread implements Runnable {
	private static final Logger log = Logger.getLogger( ServerExecutionThread.class );
	private ISocketServer server;

	public ServerExecutionThread( ISocketServer server ) {
		this.server = server;
	}
	
	@Override
	public void run() {
		for ( ; ;  ) {
            try {
                ISocketAdapter clientSocket = this.server.getSocket().accept();

                ApplicationResult policyResult = this.server.checkPolicy( this.server.getProtocol().getClass(), PolicyType.ON_CONNECTION );
                if ( policyResult.isSuccessful() || policyResult.isVoid() ) {
                    // @FIXME: aaa!!!
                    IClientsProcessor processor = null; // this.server.getProtocol().createClientsProcessor(this.server); 
                	processor.onConnection(clientSocket);
                } else {
                    if ( policyResult.isException() ) {
                        log.error("Connection policy crashed with exception", policyResult.getException() );    
                    }

                    this.server.refuseConnection(clientSocket);
                }
            } catch ( Throwable e ) {
                log.error( e.getMessage(), e );
            }
        }
	}

}
