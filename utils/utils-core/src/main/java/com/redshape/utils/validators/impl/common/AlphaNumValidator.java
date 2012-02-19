package com.redshape.utils.validators.impl.common;

import com.redshape.utils.validators.AbstractValidator;
import com.redshape.utils.validators.result.IValidationResult;
import com.redshape.utils.validators.result.ValidationResult;

import java.util.regex.Pattern;

/**
 * @package com.redshape.validators.impl.common
 * @user cyril
 * @date 7/20/11 8:59 AM
 */
public class AlphaNumValidator extends AbstractValidator<String, IValidationResult> {
    private Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");

    @Override
    public boolean isValid(String value) {
        return value == null || value.isEmpty() || this.pattern.matcher(value).find();
    }

    @Override
    public IValidationResult validate(String value) {
        return new ValidationResult( this.isValid(value), "Only [a-zA-Z0-9_] symbols allowed!");
    }
}
