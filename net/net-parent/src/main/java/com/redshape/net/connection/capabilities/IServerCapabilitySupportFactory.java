package com.redshape.net.connection.capabilities;

import com.redshape.net.capability.CapabilityType;
import com.redshape.net.connection.ConnectionException;
import com.redshape.net.connection.IServerConnection;

/**
 * @author nikelin
 * @date 15:53
 */
public interface IServerCapabilitySupportFactory {

    public IFilesystemCapabilitySupport getFilesystemCapability( IServerConnection connection );

    public IConsoleCapabilitySupport getConsoleCapability( IServerConnection connection );

    public void registerSupport( CapabilityType type, Class<? extends IServerCapabilitySupport> support );
    
    public <T extends IServerCapabilitySupport> T getCapability( CapabilityType capability,
                                                                 IServerConnection connection )
        throws ConnectionException;

}
