/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
