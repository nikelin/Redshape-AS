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

package com.redshape.servlet.core.restrictions;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 7/2/12
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ContextRestriction {

    /**
     * @see com.redshape.servlet.core.context.ContextId
     * @return
     */
    public String[] value() default "ContextId.AJAX";

}
