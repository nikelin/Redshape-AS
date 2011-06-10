package com.redshape.validators.impl;

import com.redshape.utils.AnnotatedObject;
import com.redshape.validators.IValidator;
import com.redshape.validators.ValidatorsFacade;
import com.redshape.validators.annotations.Validator;
import com.redshape.validators.result.ValidationResult;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators.impl
 */
public class ValidatorAnnotationValidator implements IValidator<AnnotatedObject, ValidationResult> {

	@Override
	public boolean isValid(AnnotatedObject value) {
		Validator annotation = value.getAnnotation( Validator.class );

		IValidator<Object,?> validator = ValidatorsFacade.getInstance().getValidator( annotation.value() );
		if ( validator == null ) {
			return true;
		}

		return validator.isValid( value.getContext() );
	}

	@Override
	public ValidationResult validate(AnnotatedObject value) {
		return new ValidationResult( this.isValid( value ) );
	}
}
