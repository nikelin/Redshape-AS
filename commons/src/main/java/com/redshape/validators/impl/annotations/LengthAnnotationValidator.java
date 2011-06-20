package com.redshape.validators.impl.annotations;

import com.redshape.utils.AnnotatedObject;
import com.redshape.validators.AbstractValidator;
import com.redshape.validators.annotations.Length;
import com.redshape.validators.impl.annotations.result.ValidationResult;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators.impl
 */
public class LengthAnnotationValidator extends AbstractValidator<AnnotatedObject, ValidationResult> {

	@Override
	public boolean isValid(AnnotatedObject value) {
		Length annotation = value.getAnnotation( Length.class );
		if ( !( value.getContext() instanceof String ) ) {
			return true;
		}

		return annotation.max() >= value.<String>getContext().length()
				&& annotation.min() >= value.<String>getContext().length();

	}
	
	@Override
	public ValidationResult validate(AnnotatedObject value) {
		return new ValidationResult( this.isValid(value) );
	}
}
