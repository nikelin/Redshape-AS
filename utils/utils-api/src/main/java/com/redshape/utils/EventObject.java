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

package com.redshape.utils;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 8, 2010
 * Time: 12:38:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class EventObject {
    private Object id;
    private Object[] params;


    public EventObject( Object id, Object...params ) {
        this.id = id;
        this.params = params;
    }

    public Object getId() {
        return this.id;
    }

    public Object[] getParams() {
        return this.params;
    }

}
