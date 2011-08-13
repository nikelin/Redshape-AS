package com.redshape.commands;

import com.redshape.applications.bootstrap.IBootstrapAction;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 21, 2010
 * Time: 5:56:20 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractCommand implements ICommand {
    private Map<String, String> properties = new HashMap<String, String>();
    private Set<IBootstrapAction> bootstrapActions = new HashSet<IBootstrapAction>(); 

    public Map<String, String> getProperties() {
        return this.properties;
    }

    @Override
    public boolean isSupports(String name) {
        return this.getProperties().containsKey(name);
    }

    public void setProperty( String name, String value ) {
        this.properties.put(name, value);
    }

    public String getProperty( String name ) {
        return this.properties.get(name);
    }

    public Integer getIntegerProperty( String name ) {
        String value = this.getProperty(name);
        if ( value == null || value.isEmpty() ) {
            return null;
        }

        return Integer.valueOf( value );
    }

    public boolean isValid() {
        for ( String property : this.getImportant() ) {
            String value = this.getProperty(property);
            if ( value == null || value.isEmpty() ) {
                return false;
            }
        }

        return true;
    }

    public Collection<? extends IBootstrapAction> getBootstrapRequirements() {
        return this.bootstrapActions;
    }

    protected void addRequiredAction( IBootstrapAction action ) {
        this.bootstrapActions.add(action);
    }
    
    public String[] getImportant() {
        return new String[] {};
    }

}
