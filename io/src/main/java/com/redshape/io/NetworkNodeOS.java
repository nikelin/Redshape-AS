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

package com.redshape.io;


import java.util.Collection;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 11/2/10
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */

public class NetworkNodeOS {
    private String name;

    private String family;

    private String generation;

    private boolean is_null;

    private Collection<NetworkNode> node = new HashSet();

    public NetworkNodeOS() {
        this(null, null, null);
    }

    public NetworkNodeOS( String name, String family, String generation ) {
        super();
        
        this.name = name;
        this.family = family;
        this.generation = generation;
    }

    public boolean isNull() {
        return this.is_null;
    }

    public void markNull( boolean value ) {
        this.is_null = !value;
    }

    public String getGeneration() {
        return this.generation;
    }

    public void setGeneration( String generation ) {
        this.generation = generation;
    }

    public String getFamily() {
        return this.family;
    }

    public void setFamily( String name ) {
        this.family = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name ) {
        this.name = name;
    }

}
