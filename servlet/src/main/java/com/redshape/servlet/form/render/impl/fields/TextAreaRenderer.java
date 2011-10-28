package com.redshape.servlet.form.render.impl.fields;

import com.redshape.servlet.form.RenderMode;
import com.redshape.servlet.form.fields.TextAreaField;
import com.redshape.utils.Commons;
import com.redshape.validators.result.IValidationResult;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 13.06.11
 * Time: 1:16
 * To change this template use File | Settings | File Templates.
 */
public class TextAreaRenderer extends AbstractFormFieldRenderer<TextAreaField> {

    @Override
    public String render(TextAreaField item, RenderMode mode) {
        StringBuilder builder = new StringBuilder();
        builder.append("<textarea ")
               .append("name=\"").append(Commons.select( item.getName(), item.getId() ) ).append("\" ")
               .append("id=\"").append(Commons.select( item.getId(), item.getName() ) ).append("\" ");

		Map<String, Object> params = item.getAttributes();
		if ( !item.getValidationResults().isEmpty() ) {
			for ( IValidationResult result : item.getValidationResults() ) {
				if ( !result.isValid() ) {
					String classValue = (String) params.get("class");
					if ( classValue == null ) {
						params.put("class", "error");
					} else {
						params.put("class", params.get("class") + " error" );
					}
					break;
				}
			}
		}

        this.buildAttributes(builder, item);

        builder.append(">");

        if ( item.getValue() != null ) {
            builder.append( item.getValue() );
        }

        builder.append("</textarea>");

        return this.applyDecorators( builder, item );
    }

}