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

import com.redshape.form.IForm;
import com.redshape.form.IFormField;
import com.redshape.utils.validators.AbstractValidator;
import com.redshape.utils.validators.result.IValidationResult;
import com.redshape.utils.validators.result.ValidationResult;

/**
 * @package com.redshape.form.validators
 * @user cyril
 * @date 6/25/11 1:07 PM
 */
public class RepeatFieldValidator extends AbstractValidator<String, IValidationResult> {
    private IForm form;
    private IFormField<?> source;
    private IFormField<?> target;

    public RepeatFieldValidator( IForm form, String sourcePath, String targetPath ) {
        this.form = form;
        this.source = this.form.<Object, IFormField<Object>>findField( sourcePath );
		if ( this.source == null ) {
			throw new IllegalArgumentException("<null>");
		}

        this.target = this.form.<Object, IFormField<Object>>findField( targetPath );
		if ( this.target == null ) {
			throw new IllegalArgumentException("<null>");
		}
    }

    protected IForm getForm() {
        return this.form;
    }

    protected IFormField<?> getSource() {
        return this.source;
    }

    protected IFormField<?> getTarget() {
        return this.target;
    }

    @Override
    public boolean isValid(String value) {
        return this.getSource().getValue() == null
			|| this.getTarget().getValue() == null
			|| this.getSource().getValue().equals( this.getTarget().getValue() );
    }

    @Override
    public ValidationResult validate(String value) {
        return new ValidationResult( this.isValid(value),
                this.getSource().getLabel() + " field must match value of field "
                + this.getTarget().getLabel() );
    }
}
