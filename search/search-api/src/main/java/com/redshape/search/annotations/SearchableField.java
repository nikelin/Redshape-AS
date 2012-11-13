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

package com.redshape.search.annotations;

import com.redshape.search.index.IndexingType;

import java.lang.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 2:46:56 PM
 * To change this template use File | Settings | File Templates.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Inherited
public @interface SearchableField {
    /**
     * Name of field in index
     * @return
     */
    String name();

    /**
     * Type for field contents
     * @return
     */
    IndexingType type() default IndexingType.TEXT;

    /**
     * Store or not this field in com.redshape.search index
     * @return
     */
    boolean stored() default true;

    /**
     * Process lexical analysis on current field
     * @return
     */
    boolean analyzable() default true;

    /**
     * Store value as a bytes array
     * @return
     */
    boolean binary() default false;

    /**
     * Field value for result scoring
     * @return
     */
    int rank() default 0;

}
