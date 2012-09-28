package com.redshape.utils.validators.simple;

import com.redshape.utils.validators.AbstractValidator;
import com.redshape.utils.validators.result.ValidationResult;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.validators.simple
 * @date 2/17/12 {5:56 PM}
 */
public class NumericStringValidator extends AbstractValidator<String, ValidationResult> {

    @Override
    public ValidationResult validate(String value) {
        return new ValidationResult( this.isValid(value), "Numeric value expected!" );
    }

    @Override
    public boolean isValid( String value ) {
        try {
            if ( value == null || value.isEmpty() ) {
                return true;
            }

            Double.parseDouble(value);

            return true;
        } catch ( NumberFormatException e ) {
            return false;
        }
    }

}