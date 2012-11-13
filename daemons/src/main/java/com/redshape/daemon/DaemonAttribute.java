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

package com.redshape.daemon;

import javax.xml.bind.annotation.*;

/**
 * @author nikelin
 */
@XmlType
@XmlAccessorType( XmlAccessType.PUBLIC_MEMBER )
public class DaemonAttribute {
    private String name;

    private Object value;

    public DaemonAttribute() {
        this(null, null);
    }

    public DaemonAttribute( String name, Object value ) {
        this.name = name;
        this.value = value;
    }

    public void setValue( String value ) {
        this.value = value;
    }

    public void setValue( Object value ) {
        this.value = value;
    }

    @XmlTransient
    public Object getObjectValue() {
        return this.value;
    }

    public String getValue() {
        return String.valueOf( this.value );
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
