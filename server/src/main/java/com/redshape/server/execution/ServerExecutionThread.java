package com.redshape.server.execution;

import com.redshape.io.server.policy.ApplicationResult;
import com.redshape.io.server.policy.PolicyType;
import com.redshape.server.ISocketServer;
import org.apache.log4j.Logger;

import com.redshape.io.net.adapters.socket.client.ISocketAdapter;

import com.redshape.io.protocols.core.IProtocol;

public class ServerExecutionThread<T extends IProtocol<?, ?,?,?,?,?>> implements Runnable {
	private static final Logger log = Logger.getLogger( ServerExecutionThread.class );
	private ISocketServer<T, ?, ?> server;

	public ServerExecutionThread( ISocketServer<T, ?, ?> server ) {
		this.server = server;
	}
	
	@Override
	public void run() {
		for ( ; ;  ) {
            try {
                ISocketAdapter clientSocket = this.server.getSocket().accept();

                ApplicationResult policyResult = this.server.checkPolicy( (Class<T>) this.server.getProtocol().getClass(), PolicyType.ON_CONNECTION );
                if ( policyResult.isSuccessful() || policyResult.isVoid() ) {
                	// @TODO: fixme due to Protocols API refactoring
//                    IClientsProcessor processor = this.server.getProtocol().createClientsProcessor(this.server); 
//                	processor.onConnection(clientSocket);
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
