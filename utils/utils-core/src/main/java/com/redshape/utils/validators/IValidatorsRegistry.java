package com.redshape.utils.validators;

import com.redshape.utils.AnnotatedObject;

import java.lang.annotation.Annotation;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/22/12
 * Time: 1:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IValidatorsRegistry {

    public <T> IValidator<T, ?> getValidator( Class<? extends IValidator<T,?>> clazz );

    public <T extends AnnotatedObject> IValidator<T,?> getAnnotationValidator( Class<? extends Annotation> annotation);

    public void registerAnnotationValidator(Class<? extends Annotation> annotation, IValidator<? extends AnnotatedObject, ?> validator);

}
