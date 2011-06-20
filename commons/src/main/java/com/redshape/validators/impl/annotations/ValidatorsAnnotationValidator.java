package com.redshape.validators.impl.annotations;

import com.redshape.utils.AnnotatedObject;
import com.redshape.validators.AbstractValidator;
import com.redshape.validators.IValidator;
import com.redshape.validators.ValidatorsFacade;
import com.redshape.validators.annotations.Validator;
import com.redshape.validators.annotations.Validators;
import com.redshape.validators.impl.annotations.result.ValidationResult;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators.impl
 */
public class ValidatorsAnnotationValidator extends AbstractValidator<AnnotatedObject, ValidationResult> {


	@Override
	public boolean isValid(AnnotatedObject value) {
		Validators annotation = value.getAnnotation( Validators.class );

		for ( Validator validator : annotation.value() ) {
			IValidator<AnnotatedObject, ?> instance = ValidatorsFacade.getInstance().getAnnotationValidator( validator.annotationType() );
			if ( !instance.isValid(value) ) {
				return false;
			}
		}

		return true;
	}

	@Override
	public ValidationResult validate(AnnotatedObject value) {
		return new ValidationResult( this.isValid(value) );
	}
}
