package com.redshape.servlet.form.render.impl.fields;

import com.redshape.servlet.form.IFormField;
import com.redshape.servlet.form.RenderMode;
import com.redshape.servlet.form.decorators.IDecorator;
import com.redshape.servlet.form.fields.CheckboxGroupField;

import java.util.Map;

/**
 * @package com.redshape.servlet.form.render.impl.fields
 * @user cyril
 * @date 6/27/11 10:42 PM
 */
public class CheckboxGroupFieldRenderer extends AbstractFormFieldRenderer<CheckboxGroupField<?>> {

    public CheckboxGroupFieldRenderer() {
        super();
    }

    protected void renderItem( StringBuilder builder, IFormField<?> field, String name, Object value ) {
        builder.append("<label>")
               .append(value)
                .append("</label>");

        builder.append("<input type=\"checkbox\" value=\"")
               .append(name);

        builder.append("\" name=\"").append( field.getCanonicalName() ).append("\" ");

        builder.append("/><br/>");
    }

    @Override
    public String render(CheckboxGroupField<?> item, RenderMode mode) {
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