package com.redshape.renderer.forms.renderers.fields;

import com.redshape.form.RenderMode;
import com.redshape.i18n.impl.StandardI18NFacade;
import com.redshape.form.fields.InputField;
import com.redshape.renderer.IRenderersFactory;

public class InputFieldRenderer extends AbstractFormFieldRenderer<InputField> {

    public InputFieldRenderer(IRenderersFactory renderersFactory) {
        super(renderersFactory);
    }

    @Override
    public String render(InputField field, RenderMode mode ) {
        StringBuilder builder = new StringBuilder();
        builder.append("<input ")
                .append("type=\"").append( field.getType().name().toLowerCase() ).append("\" ");

        if ( field.getId() != null ) {
            builder.append("id=\"").append( field.getId() ).append( "\" ");
        }

        if ( field.getName() != null ) {
            builder.append("name=\"").append( field.getCanonicalName() ).append( "\" ");
        }


        if ( field.getValue() != null ) {
            String value = String.valueOf( field.getValue() );
            if ( field.getType().equals( InputField.Type.SUBMIT ) || field.getType().equals( InputField.Type.RESET ) ) {
                value = StandardI18NFacade._( value );
            }

            builder.append("value=\"").append( value ).append("\"");
        }

        this.applyErrorStateIfNeeds(field);
        this.buildAttributes(builder, field);

        builder.append("/>");

        return this.applyDecorators(builder, field, mode);
    }


}
