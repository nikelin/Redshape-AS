package com.redshape.validators.impl.common;

import com.redshape.validators.AbstractValidator;
import com.redshape.validators.impl.annotations.result.ValidationResult;
import com.redshape.validators.result.IValidationResult;

public class LengthValidator extends AbstractValidator<String, IValidationResult> {
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
		if ( value == null ) {
			return false;
		}
		
		return ( max == -1 || value.length() <= max ) || ( min == -1 || value.length() >= min );
	}

	protected String selectMessage( String value ) {
        if ( value == null ) {
            return "Non-empty value expected!";
        }

		if ( value.length() > max ) {
			return String.format(
					"Max length for a value is %d but current length is %d",
					this.max, value.length() ); 
		} else if ( value.length() < min ){
			return String.format(
					"Min length for a value is %d but current length is %d",
					this.min, value.length() );
		}
		
		return "";
	}
	
	@Override
	public IValidationResult validate(String value) {
		return new ValidationResult( this.isValid(value), this.selectMessage(value) );
	}
	
}
