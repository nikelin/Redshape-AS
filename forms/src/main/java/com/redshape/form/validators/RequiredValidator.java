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

package com.redshape.form.validators;

import com.redshape.form.IFormField;
import com.redshape.utils.validators.AbstractValidator;
import com.redshape.utils.validators.result.ValidationResult;

/**
 * @package com.redshape.form.validators
 * @user cyril
 * @date 6/25/11 4:22 PM
 */
public class RequiredValidator extends AbstractValidator<String, ValidationResult> {
    private IFormField<?> field;

    public RequiredValidator( IFormField<?> field ) {
        super();

        if ( field == null ) {
            throw new IllegalArgumentException("<null>");
        }

        this.field = field;
    }

    @Override
    public boolean isValid(String value) {
        return !this.field.isRequired() || value != null;
    }

    @Override
    public ValidationResult validate(String value) {
        return new ValidationResult( this.isValid(value), "Field is required");
    }
}
