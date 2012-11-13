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

package com.redshape.utils;

import java.lang.annotation.Annotation;

/**
 * Entity to represents annotated object.
 *  
 * Holds directly annotation subject and collection of
 * annotated objects which decorated him. 
 * 
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators
 */
public class AnnotatedObject {
	private Object context;
	private Annotation[] annotations;

	public AnnotatedObject( Object context, Annotation... annotations ) {
		this.context = context;
		this.annotations = annotations;
	}

	@SuppressWarnings("unchecked")
	public <V> V getContext() {
		return (V) this.context;
	}

	@SuppressWarnings("unchecked")
	public <V extends Annotation> V getAnnotation( Class<V> annotationClazz ) {
		for ( Annotation annotation : this.getAnnotations() ) {
			if ( annotation.annotationType().equals( annotationClazz ) ) {
				return (V) annotation;
			}
		}

		return null;
	}

	public Annotation[] getAnnotations() {
		return this.annotations;
	}

}
