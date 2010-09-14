package com.redshape.notifications.transport.email.configuration;

import com.redshape.notifications.transport.configuration.AbstractTransportConfiguration;
import com.redshape.notifications.transport.configuration.ConfigurationException;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 12, 2010
 * Time: 3:15:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailTransportConfiguration extends AbstractTransportConfiguration {
    private static String[] supportedProperties = new String[] { "host", "user", "port", "credentials"};

    public void setPort( Integer port ) throws ConfigurationException {
        this.setProperty( "port", port );
    }

    public Integer getPort() throws ConfigurationException {
        return this.getIntegerProperty("port");
    }

    public String getHost() throws ConfigurationException {
        return this.getStringProperty("host");
    }

    public void setHost( String host ) throws ConfigurationException {
        this.setProperty("host", host);
    }

    public String getUser() {
        return String.valueOf( this.getProperty("user") );
    }

    public void setUser( String user ) throws ConfigurationException {
        this.setProperty("user", user);
    }

    public String getCredentials() {
        return String.valueOf( this.getProperty("credentials") );
    }

    public void setCredentials( String credentials ) throws ConfigurationException {
        this.setProperty("credentials", credentials);
    }

    public boolean isSupports( String name ) {
        return Arrays.binarySearch( supportedProperties, name ) != -1;
    }

}
