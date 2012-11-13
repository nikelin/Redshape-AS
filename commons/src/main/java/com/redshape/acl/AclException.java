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

package com.redshape.acl;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 6, 2010
 * Time: 6:37:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class AclException extends Exception {
	private static final long serialVersionUID = -6367258819272631108L;

	public AclException() {
        super();
    }

    public AclException( String message ) {
        super(message);
    }

}
