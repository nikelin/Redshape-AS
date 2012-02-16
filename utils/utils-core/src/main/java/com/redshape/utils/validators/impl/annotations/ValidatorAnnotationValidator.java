package com.redshape.utils.validators.impl.annotations;

import com.redshape.utils.AnnotatedObject;
import com.redshape.utils.validators.AbstractValidator;
import com.redshape.utils.validators.IValidator;
import com.redshape.utils.validators.ValidatorsFacade;
import com.redshape.utils.validators.annotations.Validator;
import com.redshape.utils.validators.impl.annotations.result.ValidationResult;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators.impl
 */
public class ValidatorAnnotationValidator extends AbstractValidator<AnnotatedObject, ValidationResult> {

	@Override
	public boolean isValid(AnnotatedObject value) {
		Validator annotation = value.getAnnotation( Validator.class );

		IValidator<Object,?> validator = ValidatorsFacade.getInstance().<Object>getValidator( (Class<? extends IValidator<Object, ?>>) annotation.value() );
		if ( validator == null ) {
			return true;
		}

		return validator.isValid( value.<AnnotatedObject>getContext() );
	}

	@Override
	public ValidationResult validate(AnnotatedObject value) {
		return new ValidationResult( this.isValid( value ) );
	}
}
