package com.redshape.servlet.form.validators;

import com.redshape.i18n.impl.StandardI18NFacade;
import com.redshape.servlet.form.IForm;
import com.redshape.servlet.form.IFormField;
import com.redshape.validators.AbstractValidator;
import com.redshape.validators.impl.common.ValidationResult;

/**
 * @package com.redshape.servlet.form.validators
 * @user cyril
 * @date 6/25/11 1:07 PM
 */
public class RepeatFieldValidator extends AbstractValidator<String, ValidationResult> {
    private IForm form;
    private String sourcePath;
    private String targetPath;

    public RepeatFieldValidator( IForm form, String sourcePath, String targetPath ) {
        this.form = form;
        this.sourcePath = sourcePath;
        this.targetPath = targetPath;
    }

    protected IForm getForm() {
        return this.form;
    }

    protected String getSourcePath() {
        return this.sourcePath;
    }

    protected String getTargetPath() {
        return this.targetPath;
    }

    protected IFormField<?> getSource() {
        return this.form.findField( this.getSourcePath() );
    }

    protected IFormField<?> getTarget() {
        return this.form.findField( this.getTargetPath() );
    }

    @Override
    public boolean isValid(String value) {
        return this.getSource().getValue().equals( this.getTarget().getValue() );
    }

    @Override
    public ValidationResult validate(String value) {
        return new ValidationResult( this.isValid(value),
                String.format(
                    StandardI18NFacade._("%s field must match value of field %s"),
                    this.getSource().getLabel(),
                    this.getTarget().getLabel() ) );
    }
}
