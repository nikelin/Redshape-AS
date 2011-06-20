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
