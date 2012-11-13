/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.commands;

import com.redshape.applications.bootstrap.IBootstrapAction;

import java.io.Writer;
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
    private Writer writer;

    protected Writer getWriter() {
        return writer;
    }

    @Override
    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public Map<String, String> getProperties() {
        return this.properties;
    }

    @Override
    public void setProperty( String name, String value ) {
        this.properties.put(name, value);
    }

    @Override
    public String getProperty( String name ) {
        return this.properties.get(name);
    }

    @Override
    public boolean hasProperty( String name ) {
        return this.properties.containsKey(name);
    }

    protected Integer getIntegerProperty( String name ) {
        String value = this.getProperty(name);
        if ( value == null || value.isEmpty() ) {
            return null;
        }

        return Integer.valueOf( value );
    }

    @Override
    public boolean isValid() {
        for ( String property : this.getImportant() ) {
            String value = this.getProperty(property);
            if ( value == null || value.isEmpty() ) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Collection<? extends IBootstrapAction> getBootstrapRequirements() {
        return this.bootstrapActions;
    }

    protected void addRequiredAction( IBootstrapAction action ) {
        this.bootstrapActions.add(action);
    }

    @Override
    public String[] getImportant() {
        return new String[] {};
    }

    @Override
    public boolean isSupports(String name) {
        for ( String property : this.getSupported() ) {
            if ( property.equals(name) ) {
                return true;
            }
        }

        return false;
    }

}
