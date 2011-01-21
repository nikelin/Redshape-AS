package com.redshape.io;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 11/2/10
 * Time: 5:07 PM
 * To change this template use File | Settings | File Templates.
 */

public class NetworkNodePort {

    private Integer portId;

    private String protocol;

    private String service;

    private Boolean state;

    private Collection<NetworkNode> nodes = new HashSet();

    public NetworkNodePort() {
        this(null);
    }

    public NetworkNodePort( Integer portId ) {
        this(portId, null);
    }

    public NetworkNodePort( Integer portId, String protocol ) {
        super();

        this.portId = portId;
        this.protocol = protocol;
    }


    public void setState( Boolean state ) {
        this.state = state;
    }

    public Boolean isClosed() {
        return !this.state;
    }

    public Integer getPortId() {
        return this.portId;
    }

    public void setService( String service ) {
        this.service = service;
    }

    public String getService() {
        return this.service;
    }

    public void setProtocol( String protocol ) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public void setNodes( Collection<NetworkNode> nodes ) {
        this.nodes = nodes;
    }

    public Collection<NetworkNode> getNodes() {
        return this.nodes;
    }
}
