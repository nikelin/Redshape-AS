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

package com.redshape.utils.beans.bindings.annotations;

import java.lang.annotation.*;
import java.lang.annotation.ElementType;

import com.redshape.utils.beans.bindings.types.BindableType;

@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.FIELD, ElementType.METHOD } )
@Inherited
public @interface Bindable {
	
	public String id() default "";
	
	public BindableType type() default BindableType.NONE;
	
	public Class<?>[] targetType() default {};
	
	public String name() default "";

    public BindableAttributes[] attributes() default {};
	
}
