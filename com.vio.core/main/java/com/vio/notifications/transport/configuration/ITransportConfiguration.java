package com.vio.notifications.transport.configuration;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 12, 2010
 * Time: 3:14:14 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITransportConfiguration {

    /**
     * Set value to property by name to value
     * 
     * @param name
     * @param value
     */
    public void setProperty( String name, Object value ) throws ConfigurationException;

    /**
     * Get single property from current configuration set by
     * it's name
     * 
     * @param name
     */
    public Object getProperty( String name );

    public String getStringProperty( String name ) throws ConfigurationException;

    public Integer getIntegerProperty( String name ) throws ConfigurationException; 

    /**
     * Does given property name exists in current configuration set
     * 
     * @param name
     * @return
     */
    public boolean isSupports( String name );

}
