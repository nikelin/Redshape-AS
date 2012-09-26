package com.redshape.utils.validators.impl;

import com.redshape.utils.AnnotatedObject;
import com.redshape.utils.Commons;
import com.redshape.utils.validators.IValidator;
import com.redshape.utils.validators.IValidatorsRegistry;
import com.redshape.utils.validators.impl.annotations.AbstractAnnotationValidator;
import org.apache.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.utils.validators
 */
public class StandardValidatorsRegistry implements IValidatorsRegistry {
	private static final Logger log = Logger.getLogger(StandardValidatorsRegistry.class);

	private Map<Class<? extends IValidator<?,?>>, IValidator<?,?>> validators
			= new HashMap<Class<? extends IValidator<?,?>>, IValidator<?,?>>();
	private Map<Class<? extends Annotation>, IValidator<? extends AnnotatedObject,?>> annotationValidators
			= new HashMap<Class<? extends Annotation>, IValidator<? extends AnnotatedObject,?>>();

    public StandardValidatorsRegistry( Collection<Class<? extends IValidator<?, ?>>> validators ) {
        Commons.checkNotNull(validators);

        this.initValidators(validators);
    }

	@Override
	public <T> IValidator<T, ?> getValidator( Class<? extends IValidator<T,?>> clazz ) {
        Commons.checkNotNull(clazz);

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

    @Override
	public <T extends AnnotatedObject> IValidator<T,?> getAnnotationValidator( Class<? extends Annotation> annotation) {
		return (IValidator<T, ?>) this.annotationValidators.get( annotation );
	}

    @Override
	public void registerAnnotationValidator(Class<? extends Annotation> annotation,
								  IValidator<? extends AnnotatedObject, ?> validator) {
        Commons.checkNotNull(annotation);
        Commons.checkNotNull(validator);

        this.annotationValidators.put( annotation, validator);
	}

    protected void initValidators( Collection<Class<? extends IValidator<?, ?>>> validators ) {
        for ( Class<? extends IValidator<?, ?>> clazz : validators ) {
            Constructor<?> targetConstructor = null;
            boolean registryAwared = false;
            for (Constructor<?> constructor : clazz.getConstructors() ) {
                if ( constructor.getParameterTypes().length == 0 ) {
                    targetConstructor = constructor;
                    break;
                } else if ( IValidatorsRegistry.class.isAssignableFrom( constructor.getParameterTypes()[0] ) ) {
                    targetConstructor = constructor;
                    registryAwared = true;
                    break;
                }
            }

            if ( targetConstructor == null ) {
                log.warn("Unable to find appropriate annotation validator type initializer: " + clazz.getCanonicalName() );
                continue;
            }

            IValidator validator;
            try {
                if ( registryAwared ) {
                    validator = (IValidator) targetConstructor.newInstance(this);
                } else {
                    validator = (IValidator)  targetConstructor.newInstance();
                }
            } catch ( Throwable e ) {
                log.error("Unable to initialize annotation validator type: " + clazz.getCanonicalName(), e );
                continue;
            }

            this.validators.put( clazz, validator );

            if ( validator instanceof AbstractAnnotationValidator ) {
                this.registerAnnotationValidator(
                    ((AbstractAnnotationValidator<?, ?>) validator).getAnnotation(),
                    (IValidator<? extends AnnotatedObject, ?>) validator
                );
            }
        }
    }
}
