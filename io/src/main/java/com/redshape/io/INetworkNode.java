package com.redshape.io;



import java.net.InetAddress;
import java.util.Collection;

/**
 * @author nikelin
 */
public interface INetworkNode {

    public InetAddress getNetworkPoint();

    public void setNetworkPoint(InetAddress point);

    public void addPort(NetworkNodePort port);

    public Collection<NetworkNodePort> getPorts();

    public boolean hasPort(Integer port);

    public void setOS(NetworkNodeOS os);

    public NetworkNodeOS getOS();

    public PlatformType getPlatformType();

    public void setPlatformType( PlatformType type );

}
