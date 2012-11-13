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

package com.redshape.servlet.dispatchers;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/10/10
 * Time: 11:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class DispatchException extends Exception {
	private static final long serialVersionUID = 9148190945138130478L;

	public DispatchException() {
        this(null);
    }

    public DispatchException( String message ) {
        this( message, null );
    }
    
    public DispatchException( String message, Throwable e ) {
    	super(message, e);
    }

}
