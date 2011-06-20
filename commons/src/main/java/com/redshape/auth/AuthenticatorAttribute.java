package com.redshape.auth;

import com.redshape.utils.IEnum;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 13.06.11
 * Time: 12:15
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticatorAttribute implements IEnum<String> {
    private String name;
    private Class<?> type;

    protected AuthenticatorAttribute( String name ) {
        this(name, null);
    }

    protected AuthenticatorAttribute( String name, Class<?> type ) {
        if ( name == null ) {
            throw new IllegalArgumentException("<null>");
        }

        this.name = name;
        this.type = type;
    }

    public boolean isAllowed( Object value ) {
        return type == null || type.isAssignableFrom( value.getClass() );
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals( Object value ) {
        return !( value == null ) && (value instanceof IEnum)
                && ( (IEnum<?>) value).name().equals( this.name );
    }

    public static class Session extends AuthenticatorAttribute {

        public Session( String name, Class<?> type ) {
            super(name, type);
        }

        public static final Session Id = new Session("Authenticator.Session.Id", Object.class );
        public static final Session Save = new Session("Authenticator.Session.Save", Boolean.class );
        public static final Session Duration = new Session("Authenticator.Session.Duration", Integer.class );

    }

}
