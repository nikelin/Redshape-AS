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

package com.redshape.utils.validators.annotations;

import java.lang.annotation.*;

/**
 * Annotation which needs to provide ability of marking target entity
 * field or accessor which length must be limited within interval {min,max}.
 * 
 * This is polymorphic annotation and it's target type must not only be a String but also
 * all types of object with ability to detect it's size.
 * Internal logic of validation can be founded in the implementation @see com.redshape.validators.impl.RangeValidators.
 *  
 * Validation policy for a current annotation can be replaced in @see com.redshape.validators.ValidatorsFacade
 * 
 * Often used with a @see com.redshape.validators.BeansValidator.
 * 
 * @see com.redshape.utils.validators.impl.annotations.ValidatorAnnotationValidator
 * @see com.redshape.utils.validators.impl.annotations.ValidatorsAnnotationValidator
 * 
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.bindings.annotations.validation
 */
@Inherited
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Length {

	public int min();

	public int max();

}
