package com.redshape.renderer.forms.renderers.fields;

import com.redshape.form.RenderMode;
import com.redshape.form.fields.CheckboxField;
import com.redshape.renderer.IRenderersFactory;

public class CheckboxFieldRenderer extends AbstractFormFieldRenderer<CheckboxField> {

    public CheckboxFieldRenderer(IRenderersFactory renderersFactory) {
        super(renderersFactory);
    }

    @Override
    public String render(CheckboxField field, RenderMode mode) {
        StringBuilder builder = new StringBuilder();
        builder.append("<input ")
                .append(" type=\"checkbox\" ");

        builder.append("name=\"").append( field.getCanonicalName() ).append("\" ");

        if ( field.getId() != null ) {
            builder.append("id=\"").append( field.getId() ).append("\" ");
        }

        if ( field.getValue() != null ) {
            builder.append("checked=\"checked\"");
        }

        this.applyErrorStateIfNeeds(field);

        this.buildAttributes( builder, field );

        builder.append("/>");

        return this.applyDecorators(field, builder, mode);
    }

}
