package com.redshape.daemon.traits;

import com.redshape.daemon.IDaemonAttributes;

/**
 * Interface for daemons which have ability to be located by remote
 * clients based on theirs host and port, with optional 'path' part.
 * 
 * @author nikelin
 *
 * @TODO: add setters
 * @param <T>
 */
public interface IBindableDaemon<T extends IDaemonAttributes> 
					extends IDaemon<T> {
	/**
	 * Return host for connection string
	 * @return
	 */
	public String getHost();

	public void setHost(String host);
	
	/**
	 * Return port for connection string
	 * @return
	 */
	public Integer getPort();

	public void setPort(Integer port);
	
	/**
	 * Return path for connection string
	 * @return
	 */
	public String getPath();

	public void setPath(String path);
	
	/**
	 * Return maximal parallel client connections to current com.redshape.daemon thread
	 * @return
	 */
	public Integer getMaxConnections();

	public void setMaxConnections(Integer connections);

	public Integer getMaxAttempts();

}
