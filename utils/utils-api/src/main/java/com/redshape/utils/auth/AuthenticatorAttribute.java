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
