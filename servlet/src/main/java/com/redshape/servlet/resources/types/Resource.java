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
