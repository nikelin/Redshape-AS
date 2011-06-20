package com.redshape.daemon.services;

import com.redshape.utils.events.AbstractEventDispatcher;
import com.redshape.daemon.DaemonException;
import com.redshape.daemon.events.ServiceBindExceptionEvent;
import com.redshape.daemon.traits.IBindableDaemon;
import org.apache.log4j.Logger;

import java.rmi.ConnectException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author nikelin
 * @date 13/04/11
 * @package com.redshape.daemon.services
 */
public class Connector<T> extends AbstractEventDispatcher {
	private static final Logger log = Logger.getLogger(Connector.class);
	private static final int DEFAULT_MAX_ATTEMPTS = 5;

	private int defaultMaxAttempts;

	public Connector() {
		this(DEFAULT_MAX_ATTEMPTS);
	}

	public Connector( int defaultMaxAttempts ) {
		this.defaultMaxAttempts = defaultMaxAttempts;
	}

	protected String prepareServiceLocation( String host, Integer port, String path ) {
		return "rmi://" + host + ":" + port + ( path.startsWith("/") ? path : "/" + path );
	}

	public T find( String host, Integer port, String path ) {
		return this.find( host, port, path, this.defaultMaxAttempts );
	}

	public T find( String host, Integer port, String path, Integer maxAttempts ) {
		int sleepTime = 0;
		int connectionAttempts = 0;

		while ( connectionAttempts < maxAttempts ) {
			try {
				try {
					Thread.sleep( sleepTime );
				} catch ( InterruptedException e ) {}

				try {
					// refactor with delayed ThreadExecutor
					Thread.sleep(sleepTime);
				} catch ( InterruptedException e ) {
					log.error("Sleep interrupted...");
				}

				String locationURI = this.prepareServiceLocation(host, port, path);
				log.info("Starting lookup for a: " + locationURI );

				Registry registry = LocateRegistry.getRegistry(host, port);
				String[] list = registry.list();

				return (T) registry.lookup( path.startsWith("/") ? path.substring(1) : path );
			} catch ( NotBoundException e ) {
				log.info("Cannot establish connection with deploy server...", e);
			} catch ( ConnectException e ) {
				log.info("Cannot establish connection with deploy server...", e);
			} catch ( RemoteException e ) {
				this.raiseEvent( new ServiceBindExceptionEvent( new DaemonException("Deployer server location is malformed", e) ) );
				return null;
			} finally {
				log.info("Couldn't locate remote deploy server. Sleeping for a " + (sleepTime / 1000) + "s ..." );
				sleepTime += 1000;
				connectionAttempts += 1;
			}
		}

		this.raiseEvent( new ServiceBindExceptionEvent( new Exception("Server is unreachable") ) );

		return null;
	}
}
