package com.redshape.utils.validators.impl.annotations;

import com.redshape.utils.AnnotatedObject;
import com.redshape.utils.validators.IValidator;
import com.redshape.utils.validators.IValidatorsRegistry;
import com.redshape.utils.validators.annotations.Validator;
import com.redshape.utils.validators.annotations.Validators;
import com.redshape.utils.validators.impl.annotations.result.ValidationResult;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators.impl
 */
public class ValidatorsAnnotationValidator extends AbstractAnnotationValidator<AnnotatedObject, ValidationResult> {

    public ValidatorsAnnotationValidator(IValidatorsRegistry registry) {
        super(Validators.class, registry);
    }

    @Override
	public boolean isValid(AnnotatedObject value) {
		Validators annotation = value.getAnnotation( Validators.class );

		for ( Validator validator : annotation.value() ) {
			IValidator<AnnotatedObject, ?> instance = this.getRegistry().getAnnotationValidator( validator.annotationType() );
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
