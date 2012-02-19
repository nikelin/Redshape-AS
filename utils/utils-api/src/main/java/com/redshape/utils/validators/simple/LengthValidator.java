package com.redshape.utils.validators.simple;

import com.redshape.utils.validators.AbstractValidator;
import com.redshape.utils.validators.result.ValidationResult;

public class LengthValidator extends AbstractValidator<String, ValidationResult> {
    private int min;
    private int max;

    public LengthValidator( int min, int max ) {
        if ( -1 == min && max == -1 ) {
            throw new IllegalArgumentException("Either min or max value" +
                    " must have non-negative value");
        }

        this.min = min;
        this.max = max;
    }

    @Override
    public boolean isValid(String value) {
        return ( value == null || value.isEmpty() ) || ( ( max == -1 || value.length() <= max ) || ( min == -1 || value.length() >= min ) );
    }

    protected String selectMessage( String value ) {
        if ( value == null ) {
            return "";
        }

        if ( value.length() > max ) {
            return "Max length for a value is " + this.max + " but current length is " + value.length();
        } else if ( value.length() < min ){
            return "Min length for a value is " + this.min + " but current length is " + value.length();
        }

        return "";
    }

    @Override
    public ValidationResult validate(String value) {
        return new ValidationResult( this.isValid(value), this.selectMessage(value) );
    }

}
