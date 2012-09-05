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
