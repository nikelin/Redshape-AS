package com.redshape.net.j2ee;

import com.redshape.net.capability.CapabilityType;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 3/15/12
 * Time: 5:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class TomcatCapabilityType extends CapabilityType {

    protected TomcatCapabilityType(String code) {
        super(code);
    }

    public static final TomcatCapabilityType Deploy = new TomcatCapabilityType("Server.Tomcat.Capability.Deploy");
    public static final TomcatCapabilityType Undeploy = new TomcatCapabilityType("Server.Tomcat.Capability.Undeploy");

}
