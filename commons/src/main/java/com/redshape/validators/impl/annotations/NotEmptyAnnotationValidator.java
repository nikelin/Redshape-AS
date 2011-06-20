package com.redshape.validators.impl.annotations;

import com.redshape.utils.AnnotatedObject;
import com.redshape.validators.AbstractValidator;
import com.redshape.validators.impl.annotations.result.ValidationResult;

import java.util.Collection;
import java.util.Map;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators.impl
 */
public class NotEmptyAnnotationValidator extends AbstractValidator<AnnotatedObject, ValidationResult> {

	@Override
	public boolean isValid(AnnotatedObject value) {
		if( value.getContext() instanceof Collection ) {
			return !value.<Collection<?>>getContext().isEmpty();
		} else if ( value.getContext() instanceof Map ) {
			return !value.<Map<?,?>>getContext().isEmpty();
		} else {
			return true;
		}
	}

	@Override
	public ValidationResult validate(AnnotatedObject value) {
		return new ValidationResult( this.isValid(value) );
	}
}
