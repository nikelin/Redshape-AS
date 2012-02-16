package com.redshape.renderer.forms.renderers.fields;

import com.redshape.form.IFormField;
import com.redshape.form.RenderMode;
import com.redshape.form.fields.RadioGroupField;
import com.redshape.renderer.IRenderersFactory;

import java.util.Map;

/**
 * @package com.redshape.renderer.forms.impl.fields
 * @user cyril
 * @date 7/12/11 7:31 PM
 */
public class RadioGroupFieldRenderer extends AbstractFormFieldRenderer<RadioGroupField> {

    public RadioGroupFieldRenderer(IRenderersFactory renderersFactory) {
        super(renderersFactory);
    }

    protected void renderItem( StringBuilder builder, IFormField<?> field, String name, Object value ) {
        builder.append("<label>")
                .append(name)
                .append("</label>");

        builder.append("<input type=\"radio\" value=\"")
                .append(value)
                .append("\" ");

        if ( field.getValue() != null && field.getValue().equals( value ) ) {
            builder.append(" checked=\"checked\" ");
        }

        builder.append("name=\"").append( field.getCanonicalName() ).append("\" ");

        this.applyErrorStateIfNeeds(field);
        this.buildAttributes( builder, field );


        builder.append("/><br/>");
    }

    @Override
    public String render(RadioGroupField item, RenderMode mode) {
        StringBuilder builder = new StringBuilder();
        Map<String, Object> options = (Map<String, Object>) item.getOptions();
        for ( String option : options.keySet() ) {
            this.renderItem( builder, item, option, options.get(option) );
        }

        return this.applyDecorators(builder, item, mode);
    }

}
