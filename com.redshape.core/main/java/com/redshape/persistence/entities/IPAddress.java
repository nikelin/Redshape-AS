package com.redshape.persistence.entities;

import javax.persistence.Basic;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.net.InetAddress;

@javax.persistence.Entity(name = "ip_addresses")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"address"})
        }
)
public class IPAddress extends AbstractEntity<IPAddress> implements IAddress<String> {

    @Basic
    private String address;

    public IPAddress() {
    }

    public IPAddress( String address ) {
        this.setAddress(address);
    }

    public IPAddress( InetAddress address ) {
        this(address.getAddress());
    }

    public IPAddress( byte[] address ) {
        this.setAddress(address);
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress( InetAddress address ) {
        this.setAddress(address.getAddress());
    }

    public void setAddress( byte[] address ) {
        this.setCleanAddress(address);
    }

    public void setAddress( String address ) {
        this.address = address;
    }

    public boolean equals(IPAddress o) {
        return o.getAddress().equals(this.getAddress()) || super.equals(o);
    }

    public int hashCode() {
        return this.address.hashCode();
    }

    public byte[] getCleanAddress() {
        byte[] numerate = new byte[4];//for ip V6 we must re write this
        String[] havedString = address.trim().split(" ");
        for ( int i = 0; i < havedString.length; i++ ) {
            numerate[i] = Byte.valueOf(havedString[i]);
        }
        return numerate;
    }

    public void setCleanAddress( String address ) {
        String[] splitedAddress = address.split(".");
        StringBuffer sb = new StringBuffer();
        for ( int i = 0; i < splitedAddress.length; i++ ) {
            sb.append(splitedAddress[i] + " ");
        }

        this.address = sb.toString();
    }

    public void setCleanAddress( byte[] cleanAddressTemp ) {
        StringBuffer sb = new StringBuffer();
        for ( int i = 0; i < cleanAddressTemp.length; i++ ) {
            sb.append(cleanAddressTemp[i] + " ");
        }

        address = sb.toString();
    }
}
