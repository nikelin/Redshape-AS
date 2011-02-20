package com.redshape.persistence.entities;

import javax.persistence.Basic;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.redshape.io.net.IAddress;
import com.redshape.utils.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

@javax.persistence.Entity(name = "ip_addresses")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"address"})
        }
)
public class IPAddress extends AbstractEntity implements IAddress {

    @Basic
    private InetAddress inetAddress;

    public IPAddress( String address ) throws UnknownHostException {
        this( StringUtils.stringToIP(address) );
    }

    public IPAddress( InetAddress address ) throws UnknownHostException {
        this(address.getAddress());
    }

    public IPAddress( byte[] address ) throws UnknownHostException {
    	super();
    	
        this.setInetAddress( InetAddress.getByAddress( address ) );
    }
    
    public InetAddress getInetAddress() {
        return this.inetAddress;
    }

    public void setInetAddress( InetAddress address ) {
    	this.inetAddress = address;
    }

    public boolean equals(IPAddress o) {
        return o != null && ( o.getInetAddress().equals(this.getInetAddress()) || super.equals(o) );
    }

    public int hashCode() {
        return this.getInetAddress().hashCode();
    }
}
