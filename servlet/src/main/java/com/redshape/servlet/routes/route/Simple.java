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

package com.redshape.servlet.routes.route;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.routes.IRoute;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class Simple implements IRoute {
    private String inputURI;
    private String controller;
    private String action;
    private Map<String, Object> parameters = new HashMap<String, Object>();

    public Simple( String inputURI, String controller, String action, Map<String, Object> parameters ) {
        this.inputURI = inputURI;
        this.controller = controller;
        this.action = action;
        this.parameters = parameters;
    }

    public boolean isApplicable(IHttpRequest request) {
        return this.inputURI.equals( request.getRequestURI() );
    }

    public void apply(IHttpRequest request) {
        request.setController( this.controller );
        request.setAction( this.action );
        request.setParameters( this.parameters );
    }

}
