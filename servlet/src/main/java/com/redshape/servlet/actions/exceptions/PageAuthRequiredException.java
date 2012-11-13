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

package com.redshape.servlet.actions.exceptions;

import com.redshape.servlet.core.controllers.ProcessingException;

/**
 * @package com.redshape.servlet.actions.exceptions
 * @user cyril
 * @date 6/20/11 10:42 PM
 */
public class PageAuthRequiredException extends ProcessingException {

    public PageAuthRequiredException() {
        super();
    }

    public PageAuthRequiredException(String message) {
        super(message);
    }

    public PageAuthRequiredException(String message, Throwable e) {
        super(message, e);
    }
}
