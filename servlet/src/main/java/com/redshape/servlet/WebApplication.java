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

package com.redshape.servlet;

import com.redshape.applications.ApplicationException;
import com.redshape.applications.SpringApplication;

/**
 * @author nikelin
 */
public class WebApplication extends SpringApplication {

	public WebApplication() throws ApplicationException {
		this( new String[] {} );
	}

    public WebApplication( String args[] ) throws ApplicationException {
        super( args );
    }

}
