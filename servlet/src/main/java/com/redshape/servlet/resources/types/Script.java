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

import com.redshape.utils.Commons;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 17.06.11
 * Time: 14:04
 * To change this template use File | Settings | File Templates.
 */
public class Script extends Resource {
    private String type;

    public Script( String type, String href ) {
        super(href);

        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public int hashCode() {
        return (Commons.select(this.type, "") + this.getHref()).hashCode();
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
