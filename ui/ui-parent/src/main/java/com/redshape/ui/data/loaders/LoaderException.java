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

package com.redshape.ui.data.loaders;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 23:39
 * To change this template use File | Settings | File Templates.
 */
public class LoaderException extends Exception {
	private static final long serialVersionUID = 8202624491435247164L;

	public LoaderException() {
        super();
    }

    public LoaderException( String message ) {
        super(message);
    }

    public LoaderException( String message, Throwable e ) {
        super(message, e );
    }

}
