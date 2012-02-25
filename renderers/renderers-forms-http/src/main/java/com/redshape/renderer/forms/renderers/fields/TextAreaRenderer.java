package com.redshape.renderer.forms.renderers.fields;

import com.redshape.form.RenderMode;
import com.redshape.form.fields.TextAreaField;
import com.redshape.renderer.IRenderersFactory;
import com.redshape.utils.Commons;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 13.06.11
 * Time: 1:16
 * To change this template use File | Settings | File Templates.
 */
public class TextAreaRenderer extends AbstractFormFieldRenderer<TextAreaField> {

    public TextAreaRenderer(IRenderersFactory renderersFactory) {
        super(renderersFactory);
    }

    @Override
    public String render(TextAreaField item, RenderMode mode) {
        StringBuilder builder = new StringBuilder();
        builder.append("<textarea ")
               .append("name=\"").append(Commons.select( item.getName(), item.getId() ) ).append("\" ")
               .append("id=\"").append(Commons.select( item.getId(), item.getName() ) ).append("\" ");

		this.applyErrorStateIfNeeds(item);
        this.buildAttributes(builder, item);

        builder.append(">");

        if ( item.getValue() != null ) {
            builder.append( item.getValue() );
        }

        builder.append("</textarea>");

        return this.applyDecorators( item, builder, mode );
    }

}