package com.redshape.io.net.broadcast;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: stas
 * Date: 2/1/11
 * Time: 5:56 PM
 */
public interface IDiscoveryService {

	public void stop() throws IOException;

	public void start() throws IOException;

	public Integer getPort();

	public String getHostname();

	public void setTimeout( Integer timeout );

	public Integer getTimeout();

	public void setDiscoveryInterval( int interval );

	public int getDiscoveryInterval();

	public Collection<Integer> getDiscoveryPorts();

	public void removeDiscoveryPort( Integer port );

	public void addDiscoveryPort( Integer port );

	public void setAssociatedPort( Integer port );

	public Integer getAssociatedPort();

	public boolean discoverer();

	public void discoverer( boolean value );

	public boolean discoverable();

	public void discoverable( boolean value );

}
