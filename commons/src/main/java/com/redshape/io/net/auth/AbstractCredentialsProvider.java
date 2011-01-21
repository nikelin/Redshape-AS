package com.redshape.io.net.auth;

import java.net.InetAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 11/3/10
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractCredentialsProvider implements ICredentialsProvider {
    private Map<InetAddress, Collection<ICredentials> > storage = new HashMap();
    
    synchronized public void addCredentials( InetAddress node, ICredentials credentials ) {
        if ( this.storage.get( node ) == null ) {
            this.storage.put( node, new HashSet<ICredentials>() );
        }

        this.storage.get(node).add( credentials );
    }

    public Collection<ICredentials> getCredentials( InetAddress node ) {
        return this.storage.get(node);
    }

    public Collection<ICredentials> getCredentials( InetAddress node, String serviceId ) {
        Collection<ICredentials> result = new HashSet<ICredentials>();
        for ( ICredentials item : this.getCredentials(node) ) {
            if ( item.getServiceID().equals( serviceId ) ) {
                result.add(item);
            }
        }

        return result;
    }

}
