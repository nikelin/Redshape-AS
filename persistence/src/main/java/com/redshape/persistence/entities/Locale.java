package com.redshape.persistence.entities;

import javax.persistence.*;

import com.redshape.renderer.Renderable;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 24, 2010
 * Time: 4:28:25 PM
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Entity( name = "locales" )
@Renderable
public class Locale extends AbstractEntity {
    @Basic
    private String abbr;

    @Basic
    private String localName;

    @Basic
    private String generalName;

    public void setLocalName( String name ) {
        this.localName = name;
    }

    public String getLocalName() {
        return this.localName;
    }

    public void setGeneralName( String name ) {
        this.generalName = name;
    }

    public String getGeneralName() {
        return this.generalName;
    }

    public void setAbbr( String abbr ) {
        this.abbr = abbr;
    }

    public String getAbbr() {
        return this.abbr;
    }

    public boolean equals( Locale locale ) {
        return locale != null && locale.getAbbr().equals( this.getAbbr() );
    }

    public int hashCode() {
        return this.getAbbr().hashCode();
    }
    
}
