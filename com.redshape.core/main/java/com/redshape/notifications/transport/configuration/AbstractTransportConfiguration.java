package com.redshape.notifications.transport.configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 12, 2010
 * Time: 4:35:15 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTransportConfiguration implements ITransportConfiguration {
    private Map<String, Object> properties = new HashMap();

    public void setProperty( String name, Object value ) throws ConfigurationException {
        if ( !this.isSupports(name) ) {
            throw new ConfigurationException("Property " + name + " does not supported!");
        }

        this.properties.put(name, value);
    }

    public String getStringProperty( String name ) throws ConfigurationException {
        Object value = this.getProperty(name);
        if ( value == null ) {
            return null;
        }

        if ( String.class.isAssignableFrom( value.getClass() ) ) {
            return (String) value;
        }

        String stringValue = String.valueOf(value);
        this.setProperty(name, stringValue);

        return stringValue;
    }

    public Integer getIntegerProperty( String name ) throws ConfigurationException {
        try {
            Object value = this.getProperty(name);
            if ( value == null ) {
                return null;
            }

            if ( Integer.class.isAssignableFrom( value.getClass() ) ) {
                return (Integer) value;
            }

            Integer intValue = Integer.valueOf( String.valueOf( value ) );
            this.setProperty( name, intValue );

            return intValue;
        } catch ( NumberFormatException e ) {
            throw new ConfigurationException();
        }
    }

    public Object getProperty( String name ) {
        return this.properties.get(name);                                 
    }

}
