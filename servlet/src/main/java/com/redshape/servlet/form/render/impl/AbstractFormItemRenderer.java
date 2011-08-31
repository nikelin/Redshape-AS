package com.redshape.servlet.form.render.impl;

import com.redshape.servlet.form.IFormItem;
import com.redshape.servlet.form.render.IFormItemRenderer;

import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.servlet.form.render.impl
 * @date 8/31/11 1:09 PM
 */
public abstract class AbstractFormItemRenderer<T extends IFormItem> implements IFormItemRenderer<T> {

	protected void buildAttributes( StringBuilder builder, IFormItem field ) {
		Map<String, Object> attributes = field.getAttributes();
		for ( String key : attributes.keySet() ) {
			Object value = attributes.get(key);
			if ( value == null ) {
				continue;
			}

			builder.append(" ").append( key ).append("=\"").append( attributes.get(key) ).append("\" ");
		}
	}

}
