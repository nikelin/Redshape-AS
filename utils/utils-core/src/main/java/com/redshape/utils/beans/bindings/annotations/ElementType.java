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

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.redshape.utils.beans.bindings.types.CollectionType;

@Retention( RetentionPolicy.RUNTIME )
@Target({
	java.lang.annotation.ElementType.FIELD,
	java.lang.annotation.ElementType.METHOD
})
@Inherited
public @interface ElementType {
	
	public Class<?> value();
	
	public CollectionType type() default CollectionType.LIST;
	
}
