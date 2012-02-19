package com.redshape.renderer.forms.renderers.fields;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.*;
import com.redshape.form.fields.InputField;
import com.redshape.renderer.forms.renderers.AbstractGWTRenderer;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.forms.renderers.fields
 * @date 2/17/12 {4:03 PM}
 */
public class GWTInputFieldRenderer extends AbstractGWTRenderer<InputField> {

    @Override
    public Widget render(InputField renderable) {
        Widget object;
        switch ( renderable.getType() ) {
            case PASSWORD:
                object = new PasswordTextBox();
            break;
            case HIDDEN:
                Hidden field;
                object = field = new Hidden();
                field.setName(renderable.getCanonicalName());
                field.setValue(renderable.getValue());
                field.setID(renderable.getId());
            break;
            case FILE:
                FileUpload fileField;
                object = fileField = new FileUpload();
                fileField.setName(renderable.getCanonicalName());
            break;
            case TEXT:
                TextBox textField;
                object = textField = new TextBox();
                textField.setName(renderable.getName());
                textField.setValue( renderable.getValue() );
            break;
            case SUBMIT:
                final String title = renderable.getValue();
                object = new SubmitButton( new SafeHtml() {
                    @Override
                    public String asString() {
                        return title;
                    }
                });
            break;
            case RESET:
                final String resetTitle = renderable.getValue();
                object = new ResetButton(new SafeHtml() {
                    @Override
                    public String asString() {
                        return resetTitle;
                    }
                });
            break;
            default:
                throw new IllegalArgumentException("Unsupported field type");
        }

        this.buildAttributes( object, renderable );

        return this.applyDecorators( renderable, object );
    }
}
