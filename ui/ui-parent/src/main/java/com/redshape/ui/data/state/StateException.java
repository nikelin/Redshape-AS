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

package com.redshape.ui.data.state;

import com.redshape.ui.application.UIException;

/**
 * States manager specific exceptions type
 * @author root
 * @date 07/04/11
 * @package com.redshape.ui.state
 */
public class StateException extends UIException {
	private static final long serialVersionUID = -8197639663235478395L;

	public StateException() {
        this(null);
    }

    public StateException( String message ) {
        this(message, null);
    }

    public StateException( String message, Throwable e ) {
        super(message, e);
    }

}
