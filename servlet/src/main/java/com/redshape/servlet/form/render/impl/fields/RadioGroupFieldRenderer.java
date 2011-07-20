package com.redshape.servlet.form.render.impl.fields;

import com.redshape.servlet.form.IFormField;
import com.redshape.servlet.form.RenderMode;
import com.redshape.servlet.form.decorators.IDecorator;
import com.redshape.servlet.form.fields.RadioGroupField;

import java.util.Map;

/**
 * @package com.redshape.servlet.form.render.impl.fields
 * @user cyril
 * @date 7/12/11 7:31 PM
 */
public class RadioGroupFieldRenderer extends AbstractFormFieldRenderer<RadioGroupField<?>> {

    public RadioGroupFieldRenderer() {
        super();
    }

    protected void renderItem( StringBuilder builder, IFormField<?> field, String name, Object value ) {
        builder.append("<label>")
               .append(value)
                .append("</label>");

        builder.append("<input type=\"radio\" value=\"")
               .append(name);
        builder.append("\" name=\"").append( field.getCanonicalName() ).append("\" ");

        this.buildAttributes( builder, field );


        builder.append("/><br/>");
    }

    @Override
    public String render(RadioGroupField<?> item, RenderMode mode) {
        StringBuilder builder = new StringBuilder();
        Map<String, Object> options = (Map<String, Object>) item.getOptions();
        for ( String option : options.keySet() ) {
            this.renderItem( builder, item, option, options.get(option) );
        }

        String data = builder.toString();
        for ( IDecorator decorator : item.getDecorators() ) {
            data = decorator.decorate( item, data );
        }

        return data;
    }

}
