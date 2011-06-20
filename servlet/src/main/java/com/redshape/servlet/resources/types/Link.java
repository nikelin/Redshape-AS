package com.redshape.servlet.resources.types;

import com.redshape.utils.Commons;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 17.06.11
 * Time: 14:04
 * To change this template use File | Settings | File Templates.
 */
public class Link {
    private String rel;
    private String href;
    private String type;

    public Link(String rel, String href, String type) {
        this.rel = rel;
        this.href = href;
        this.type = type;
    }

    public String getRel() {
        return rel;
    }

    public String getHref() {
        return href;
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return ( Commons.select( this.getType(), "" ) + this.getHref() ).hashCode();
    }

    @Override
    public boolean equals( Object object ) {
        if ( !( object instanceof Link ) ) {
            return false;
        }

        return ( (Link) object ).getHref().equals( this.getHref() )
                && ( (Link) object ).getType().equals( this.getType() );
    }
}
