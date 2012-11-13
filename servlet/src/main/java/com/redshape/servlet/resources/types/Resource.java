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

package com.redshape.servlet.resources.types;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 3/29/12
 * Time: 5:06 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Resource implements Serializable {

    private String href;

    protected Resource(String href) {
        this.href = href;
    }

    public void setHref( String href ) {
        this.href = href;
    }

    public String getHref() {
        return this.href;
    }

    @Override
    public int hashCode() {
        return (this.getHref()).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        if (href != null ? !href.equals(resource.href) : resource.href != null) return false;

        return true;
    }
}
