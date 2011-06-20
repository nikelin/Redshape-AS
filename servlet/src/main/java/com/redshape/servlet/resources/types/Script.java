package com.redshape.servlet.resources.types;

import com.redshape.utils.Commons;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 17.06.11
 * Time: 14:04
 * To change this template use File | Settings | File Templates.
 */
public class Script {
    private String type;
    private String href;

    public Script( String type, String href ) {
        this.href = href;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getHref() {
        return href;
    }

    @Override
    public int hashCode() {
        return (Commons.select(this.type, "") + this.href).hashCode();
    }

    @Override
    public boolean equals( Object object ) {
        if ( !( object instanceof Script ) ) {
            return false;
        }

        return  ( (Script) object).getHref().equals( this.getHref() )
                && ( (Script) object ).getType().equals( this.getType() );
    }

}
