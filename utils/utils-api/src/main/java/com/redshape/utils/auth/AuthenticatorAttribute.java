package com.redshape.utils.auth;

import com.redshape.utils.IEnum;

/**
 * @author nikelin
 * @date 13.06.11 12:15
 * @package com.redshape.utils.auth
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

        protected Session( String name, Class<?> type ) {
            super(name, type);
        }

        public static final Session Id = new Session("Authenticator.Session.Id", Object.class );
        public static final Session Save = new Session("Authenticator.Session.Save", Boolean.class );
        public static final Session Duration = new Session("Authenticator.Session.Duration", Long.class );
    }

}
