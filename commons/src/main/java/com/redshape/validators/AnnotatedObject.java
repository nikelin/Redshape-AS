package com.redshape.validators;

import java.lang.annotation.Annotation;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators
 */
public class AnnotatedObject {
	private Object context;
	private Annotation[] annotations;

	public AnnotatedObject( Object context, Annotation annotation ) {
		this(context, new Annotation[] { annotation } );
	}

	public AnnotatedObject( Object context, Annotation[] annotations ) {
		this.context = context;
		this.annotations = annotations;
	}

	public <V> V getContext() {
		return (V) this.context;
	}

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
