package com.redshape.daemon.services;

import com.redshape.daemon.IRemoteService;
import com.redshape.daemon.events.ServiceBindedEvent;
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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author nikelin
 * @date 13/04/11
 * @package com.redshape.daemon.services
 */
public class Connector<T extends IRemoteService> extends AbstractEventDispatcher {
	private static final Logger log = Logger.getLogger(Connector.class);
	private static final int DEFAULT_MAX_ATTEMPTS = 5;

    private boolean stopped;
    private Object lock = new Object();
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

    public void stop() {
        this.stopped = true;
    }
    
	public T find( String host, Integer port, String path ) {
        return this.find( host, port, path, this.defaultMaxAttempts );
	}

	public T find( final String host, final Integer port,
                   final String path, final Integer maxAttempts ) {
        synchronized (lock) {
            this.stopped = false;
            int sleepTime = 0;
            int connectionAttempts = 0;

            while ( connectionAttempts < maxAttempts && !this.stopped ) {
                try {
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
                    for ( String service : list ) {
                        log.info( String.format("Service bound withing located registry: %s", service) );
                    }

                    T result = (T) registry.lookup( path.startsWith("/") ? path.substring(1) : path );
                    this.raiseEvent( new ServiceBindedEvent( result ) );
                    return result;
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

}
