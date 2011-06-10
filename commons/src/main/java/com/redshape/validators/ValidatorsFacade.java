package com.redshape.validators;

import org.apache.log4j.Logger;

import com.redshape.utils.AnnotatedObject;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators
 */
public final class ValidatorsFacade {
	private static final Logger log = Logger.getLogger(ValidatorsFacade.class);

	private static class InstanceHolder {
		protected static final ValidatorsFacade instance = new ValidatorsFacade();
	}

	private Map<Class<? extends IValidator<?,?>>, IValidator<?,?>> validators
			= new HashMap<Class<? extends IValidator<?,?>>, IValidator<?,?>>();
	private Map<Class<? extends Annotation>, IValidator<? extends AnnotatedObject,?>> annotationValidators
			= new HashMap<Class<? extends Annotation>, IValidator<? extends AnnotatedObject,?>>();

	public static ValidatorsFacade getInstance() {
		return InstanceHolder.instance;
	}

	@SuppressWarnings("unchecked")
	public <T> IValidator<T, ?> getValidator( Class<? extends IValidator<T,?>> clazz ) {
		if ( this.validators.containsKey(clazz) ) {
			return (IValidator<T, ?>) this.validators.get( clazz );
		}

		IValidator<T,?> validator = null;
		try {
			validator = clazz.newInstance();
		} catch ( Throwable e ) {
			log.error( e.getMessage(), e );
		}

		this.validators.put( clazz, validator );

		return validator;
	}

	@SuppressWarnings("unchecked")
	public <T extends AnnotatedObject> IValidator<T,?> getAnnotationValidator( Class<? extends Annotation> annotation) {
		return (IValidator<T, ?>) this.annotationValidators.get( annotation );
	}

	public void registerAnnotationValidator(Class<? extends Annotation> annotation,
								  IValidator<? extends AnnotatedObject, ?> validator) {
		this.annotationValidators.put( annotation, validator);
	}
}
