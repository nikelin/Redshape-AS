package com.redshape.io.net.discovery;

import java.util.Collection;

import com.redshape.io.INetworkNode;


/**
 * Interface for discovering local network for accessible nodes
 *
 * @author nikelin
 */
public interface INetworkDiscoveryService {

    /**
     * Process discovery
     * @return
     */
    public Collection<INetworkNode> service() throws DiscoveryException;

    /**
     *  Check all given config attributes for validity
     * @return
     */
    public boolean checkConfig();

    /**
     *  Set configuration attribute for current discovery service implementation
     * @param name
     * @param value
     */
    public void setAttribute( String name, String value );

    /**
     * Get value of service attribute by its name
     * @param name
     * @return
     */
    public String getAttribute( String name );

    /**
     * Check for supporting given attribute by current service implementation
     * @param name
     * @return
     */
    public boolean isSupported( String name );

}
