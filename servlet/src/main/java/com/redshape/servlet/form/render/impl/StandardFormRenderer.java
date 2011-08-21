package com.redshape.servlet.form.render.impl;

import com.redshape.servlet.form.IForm;
import com.redshape.servlet.form.IFormField;
import com.redshape.servlet.form.IFormItem;
import com.redshape.servlet.form.RenderMode;
import com.redshape.servlet.form.decorators.IDecorator;
import com.redshape.servlet.form.render.IFormRenderer;
import com.redshape.servlet.views.ViewHelper;
import com.redshape.utils.Commons;

import java.util.Map;

public class StandardFormRenderer implements IFormRenderer {
	
	@Override
	public String render(IForm form, RenderMode mode) {
		StringBuilder builder = new StringBuilder();

		if ( mode.equals( RenderMode.FULL ) ) {
			if ( form.getContext() == null ) {
				builder.append("<form ")
					   .append("action=\"").append(
							   Commons.select( ViewHelper.url(form.getAction()) , "/" ) ).append("\" ")
					   .append("method=\"").append(
							   Commons.select( form.getMethod(), "POST" ) ).append("\" ");


				if ( form.getId() != null ) {
				   builder.append("id=\"")
						  .append( form.getId() )
						  .append("\"");
				}
			}  else {
				builder.append("<fieldset ");
			}

			Map<String, Object> attributes = form.getAttributes();
			for ( String key : attributes.keySet() ) {
				builder.append( key ).append("=")
					   .append("\"").append( attributes.get(key) ).append("\"");
			}

			builder.append(">\n");

			if ( form.getContext() == null ) {
				if ( form.getLegend() != null ) {
					builder.append("<legend>").append( form.getLegend() ).append("</legend>");
				}
			}
		}
		
		for ( IFormItem item : form.getItems() ) {
			if ( this.isAllowed( item, mode ) ) {
				builder.append( item.render() ).append("\n");
			}
		}

		if ( mode.equals( RenderMode.FULL ) ) {
			if ( form.getContext() == null ) {
				builder.append("</form>\n");
			} else {
				builder.append("</fieldset>");
			}
		}
		
		String data = builder.toString();
		for ( IDecorator decorator : form.getDecorators() ) {
			data = decorator.decorate(form, data);
		}
		
		return data;
	}
	
	protected boolean isAllowed( IFormItem item, RenderMode mode ) {
		switch ( mode ) {
		case FIELDS:
			if ( item instanceof IForm ) {
				return false;
			}
		break;
		case FULL:
		break;
		case SUBFORMS:
			if ( item instanceof IFormField ) {
				return false;
			}
		break;
		}
		
		return true;
	}

}
