package com.redshape.servlet.form.validators;

import com.redshape.servlet.form.IFormField;
import com.redshape.validators.AbstractValidator;
import com.redshape.validators.impl.common.ValidationResult;

/**
 * @package com.redshape.servlet.form.validators
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
