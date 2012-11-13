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

package com.redshape.utils.validators.impl.annotations;

import com.redshape.utils.Commons;
import com.redshape.utils.validators.AbstractValidator;
import com.redshape.utils.validators.IValidatorsRegistry;
import com.redshape.utils.validators.result.IValidationResult;

import java.lang.annotation.Annotation;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/22/12
 * Time: 1:26 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractAnnotationValidator<T, R extends IValidationResult> extends AbstractValidator<T, R> {

    private IValidatorsRegistry registry;
    private Class<? extends Annotation> annotation;

    protected AbstractAnnotationValidator(Class<? extends Annotation> annotation) {
        this(annotation, null);
    }

    protected AbstractAnnotationValidator( Class<? extends Annotation> annotation, IValidatorsRegistry registry ) {
        Commons.checkNotNull(annotation);

        this.annotation = annotation;
        this.registry = registry;
    }

    public Class<? extends Annotation> getAnnotation() {
        return annotation;
    }

    protected IValidatorsRegistry getRegistry() {
        return registry;
    }
}
